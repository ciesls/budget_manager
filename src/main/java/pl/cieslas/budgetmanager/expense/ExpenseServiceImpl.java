package pl.cieslas.budgetmanager.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.account.AccountService;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.user.CurrentUser;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final AccountService accountService;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, AccountService accountService) {
        this.expenseRepository = expenseRepository;
        this.accountService = accountService;
    }


    @Override
    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Optional<Expense> findByIdAndUser(Long id, User user) {
        return expenseRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Expense> findAllByUser(User user) {
        return expenseRepository.findAllByUser(user);
    }

    @Override
    public void deleteByIdAndUser(Long id, User user) {
        expenseRepository.deleteByIdAndUser(id, user);
    }

    @Override
    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    @Override
    public void update(Expense expense) {

    }

    @Override
    public List<Expense> findAllByCategoryAndUser(Category category, User user) {
        return expenseRepository.findAllByCategoryAndUser(category, user);
    }

    @Override
    public List<Expense> findAllByUserAndCreatedOnBetween(User user, LocalDate monthStart, LocalDate currentTime) {
        return expenseRepository.findAllByUserAndCreatedOnBetween(user, monthStart, currentTime);
    }

    @Override
    public List<Expense> findTop5ByUserOrderByIdDesc(User user) {
        return expenseRepository.findTop5ByUserOrderByIdDesc(user);
    }

    @Override
    public List<Expense> findAllByCategoryAndUserAndCreatedOnBetween(Category category, User user, LocalDate monthStart, LocalDate currentTime) {
        return expenseRepository.findAllByCategoryAndUserAndCreatedOnBetween(category, user, monthStart, currentTime);
    }

    @Override
    public BigDecimal sumOfExpenses(List<Expense> expenses) {
        return expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Map<YearMonth, BigDecimal> groupExpensesByMonth(List<Expense> expenses) {
        BigDecimal sumPerMonthCategory = BigDecimal.ZERO;

        Map<YearMonth, List<Expense>> groupedByMonth = expenses.stream().collect(
                Collectors.groupingBy(Expense::getYearMonth));
        Map<YearMonth, BigDecimal> groupedSum = expenses.stream().
                collect(
                        Collectors.groupingBy(Expense::getYearMonth,
                                Collectors.mapping(
                                        Expense::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        return groupedSum;

    }

    @Override
    public void updateAccountAfterExpenseDelete(User user, Expense expense) {
        Long accountId = expense.getAccount().getId();
        Optional<Account> account = accountService.findByIdAndUser(accountId, user);
        if (account.isPresent()) {
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.add(expense.getAmount()));
            accountService.save(account.get());
        } else throw new RuntimeException("Not found");
    }

    @Override
    public void updateAccountAfterExpenseAdd(User user, Expense expense) {
        Long accountId = expense.getAccount().getId();
        Optional<Account> account = accountService.findByIdAndUser(accountId, user);
        if (account.isPresent()) {
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.subtract(expense.getAmount()));
            accountService.save(account.get());
        } else throw new RuntimeException("Account not found");
    }
}
