package pl.cieslas.budgetmanager.updates;

import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.budget.BudgetService;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.util.List;
@Service
public class UpdatesServiceImpl implements Updates{

    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final ExpenseService expenseService;

    public UpdatesServiceImpl(BudgetService budgetService, CategoryService categoryService, ExpenseService expenseService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.expenseService = expenseService;
    }

    @Override
    public void setBudgetOther(Budget budget, List<Category> categories, User user) {
        Budget budgetOther = budgetService.findByNameAndUser("Other", user);
        if (budgetOther == null) {
            Budget newBudget = new Budget();
            newBudget.setName("Other");
            newBudget.setAmount(BigDecimal.ZERO);
            newBudget.setUser(user);
            budgetService.saveBudget(newBudget);
        }
        Category categoryOther = categoryService.findByNameAndUser("Other", user);
        if (categoryOther == null) {
            Category newCategory = new Category();
            newCategory.setUser(user);
            newCategory.setName("Other");
            newCategory.setBudget(budgetService.findByNameAndUser("Other", user));
            categoryService.saveCategory(newCategory);

        }
        categories = categoryService.findAllByUserAndBudget(user, budget);
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setBudget(budgetService.findByNameAndUser("Other", user));
        }
    }

    @Override
    public void setCategoryOther(Category category, List<Expense> expenses, User user) {
        Budget budgetOther = budgetService.findByNameAndUser("Other", user);
        if (budgetOther == null) {
            Budget newBudget = new Budget();
            newBudget.setName("Other");
            newBudget.setAmount(BigDecimal.ZERO);
            newBudget.setUser(user);
            budgetService.saveBudget(newBudget);
        }

        Category categoryOther = categoryService.findByNameAndUser("Other", user);
        if (categoryOther == null) {
            Category newCategory = new Category();
            newCategory.setUser(user);
            newCategory.setName("Other");
            newCategory.setBudget(budgetService.findByNameAndUser("Other", user));
            categoryService.saveCategory(newCategory);

        }

        expenses = expenseService.findAllByCategoryAndUser(category, user);
        for (Expense expense : expenses) {
            expense.setCategory(categoryService.findByNameAndUser("Other", user));

        }
    }
}
