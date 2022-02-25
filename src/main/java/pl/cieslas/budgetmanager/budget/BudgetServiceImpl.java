package pl.cieslas.budgetmanager.budget;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public BigDecimal calculateExpensesInBudgetDates(List<Category> categories, User currentUser, LocalDate monthStart, LocalDate currentTime) {
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
    public List<Expense> getBudgetExpensesDates(List<Category> categories, User user, LocalDate startTime, LocalDate endTime) {
        List<Expense> allBudgetExpenses = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            allBudgetExpenses.addAll(expenseService.findAllByCategoryAndUserAndCreatedOnBetween(categories.get(i), user, startTime, endTime));
        }
        return allBudgetExpenses;
    }

    @Override
    public void setBudgetOther(Budget budget, List<Category> categories, User user) {
        Budget budgetOther = budgetRepository.findByNameAndUser("Other", user);
        if (budgetOther == null) {
            Budget newBudget = new Budget();
            newBudget.setName("Other");
            newBudget.setAmount(BigDecimal.ZERO);
            newBudget.setUser(user);
            budgetRepository.save(newBudget);
        }
        Category categoryOther = categoryService.findByNameAndUser("Other", user);
        if (categoryOther == null) {
            Category newCategory = new Category();
            newCategory.setUser(user);
            newCategory.setName("Other");
            newCategory.setBudget(budgetRepository.findByNameAndUser("Other", user));
            categoryService.saveCategory(newCategory);

        }

        categories = categoryService.findAllByUserAndBudget(user, budget);
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setBudget(budgetRepository.findByNameAndUser("Other", user));
        }
    }
}


