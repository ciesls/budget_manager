package pl.cieslas.budgetmanager.income;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.account.AccountService;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional
public class IncomeServiceImpl implements IncomeService {

    private final IncomeRepository incomeRepository;
    private final AccountService accountService;

    @Autowired
    public IncomeServiceImpl(IncomeRepository incomeRepository, AccountService accountService) {
        this.incomeRepository = incomeRepository;
        this.accountService = accountService;
    }

    @Override
    public Optional<Income> findById(long id) {
        return incomeRepository.findById(id);
    }

    @Override
    public Optional<Income> findByIdAndUser(Long id, User user) {
        return incomeRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Income> findAllByUser(User user) {
        return incomeRepository.findAllByUser(user);
    }

    @Override
    public void deleteByIdAndUser(Long id, User user) {
        incomeRepository.deleteByIdAndUser(id, user);
    }

    @Override
    public Income save(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public List<Income> findAllByUserAndCreatedOnBetween(User user, LocalDate monthStart, LocalDate currentTime) {
        return incomeRepository.findAllByUserAndCreatedOnBetween(user, monthStart, currentTime);
    }

    @Override
    public BigDecimal sumOfIncome(List<Income> incomes) {
        return incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void updateAccountAfterIncomeAdd(User user, Income income) {
        Long accountId = income.getAccount().getId();
        Optional<Account> account = accountService.findByIdAndUser(accountId, user);
        if (account.isPresent()) {
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.add(income.getAmount()));
            accountService.save(account.get());
        }
    }

    @Override
    public void updateAccountAfterIncomeDelete(User user, Income income) {
        Long accountId = income.getAccount().getId();
        Optional<Account> account = accountService.findByIdAndUser(accountId, user);
        if (account.isPresent()) {
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.subtract(income.getAmount()));
            accountService.save(account.get());
        }
    }
}
