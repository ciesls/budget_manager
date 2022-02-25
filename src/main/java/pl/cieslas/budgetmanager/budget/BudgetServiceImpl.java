package pl.cieslas.budgetmanager.budget;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.user.CurrentUser;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@Primary
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public BudgetServiceImpl(BudgetRepository budgetRepository, ExpenseService expenseService,
                             CategoryService categoryService) {
        this.budgetRepository = budgetRepository;
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @Override
    public Optional<Budget> findById(Long id) {
        return budgetRepository.findById(id);
    }

    @Override
    public Optional<Budget> findByUserAndIdOrderByAmountDesc(User user, Long id) {
        return budgetRepository.findByUserAndIdOrderByAmountDesc(user, id);
    }

    @Override
    public List<Budget> findAllByUser(User user) {
        return budgetRepository.findAllByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        budgetRepository.deleteById(id);
    }

    @Override
    public void saveBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    @Override
    public void update(Budget budget) {
        budgetRepository.save(budget);
    }

    @Override
    public Budget findByNameAndUser(String name, User user) {
        return budgetRepository.findByNameAndUser(name, user);
    }

    @Override
    public BigDecimal calculateExpensesInBudgetDates(List<Category> categories, User currentUser,
                                                     LocalDate monthStart, LocalDate currentTime) {
        // get categories in budget, iterate and calculate sum of expenses in give list

        BigDecimal categorySum = BigDecimal.ZERO;

        for (Category category : categories) {
            List<Expense> expensesCategory = expenseService.findAllByCategoryAndUserAndCreatedOnBetween
                    (category, currentUser, monthStart, currentTime);
            categorySum = categorySum.add(expenseService.sumOfExpenses(expensesCategory));
        }
        return categorySum;
    }

    @Override
    public BigDecimal calculateExpensesInBudget(List<Category> categories, User currentUser) {
        // get categories in budget, iterate and calculate sum of expenses in give list

        BigDecimal categorySum = BigDecimal.ZERO;

        for (Category category : categories) {
            List<Expense> expensesCategory = expenseService.findAllByCategoryAndUser(category, currentUser);
            categorySum = categorySum.add(expenseService.sumOfExpenses(expensesCategory));
        }
        return categorySum;
    }

    @Override
    public List<Expense> getBudgetExpenses(List<Category> categories, User user) {
        List<Expense> allBudgetExpenses = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            allBudgetExpenses.addAll(expenseService.findAllByCategoryAndUser(categories.get(i), user));
        }
        return allBudgetExpenses;

    }

    @Override
    public List<Expense> getBudgetExpensesDates(List<Category> categories, User user, LocalDate startTime,
                                                LocalDate endTime) {
        List<Expense> allBudgetExpenses = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            allBudgetExpenses.addAll(expenseService.findAllByCategoryAndUserAndCreatedOnBetween(categories.get(i),
                    user, startTime, endTime));
        }
        return allBudgetExpenses;
    }


    @Override
    public Map<Budget, BigDecimal> getBudgetSum(User user, List<Budget> budgets, LocalDate startTime, LocalDate now) {
        budgets = budgetRepository.findAllByUser(user);
        Map<Budget, BigDecimal> budgetAmount = new HashMap<>();
        for (int i = 0; i < budgets.size(); i++) {
            List<Category> budgetCategories = categoryService.findAllByUserAndBudget(user,
                    (budgets.get(i)));
            BigDecimal budgetSum = calculateExpensesInBudgetDates(budgetCategories,
                    user, startTime, now);
            budgetAmount.put(budgets.get(i), budgetSum);
        }
        return budgetAmount;
    }
}




