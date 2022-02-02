package pl.cieslas.budgetmanager.utils.UserUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.User;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;

import java.math.BigDecimal;
@Component
public class UserUtils {

    private final BudgetService budgetService;
    private final CategoryService categoryService;

    public UserUtils(BudgetService budgetService, CategoryService categoryService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }

    public void addDefaults(User user) {
        Category category = new Category();
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setName("Other");
        category.setUser(user);
        category.setName("Other");
        budget.setAmount(BigDecimal.valueOf(0L));
        budgetService.saveBudget(budget);
        category.setBudget(budget);
        categoryService.saveCategory(category);

    }
}