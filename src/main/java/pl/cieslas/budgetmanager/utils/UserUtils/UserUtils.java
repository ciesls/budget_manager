package pl.cieslas.budgetmanager.utils.UserUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Account;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.User;
import pl.cieslas.budgetmanager.repository.account.AccountService;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class UserUtils {

    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    public UserUtils(BudgetService budgetService, CategoryService categoryService, AccountService accountService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    public void addDefaults(User user) {
        Category category = new Category();
        Budget budget = new Budget();
        Account account = new Account();
        budget.setUser(user);
        budget.setName("Other");
        category.setUser(user);
        category.setName("Other");
        budget.setAmount(BigDecimal.valueOf(0.00));
        budgetService.saveBudget(budget);
        category.setBudget(budget);
        categoryService.saveCategory(category);
        account.setUser(user);
        account.setName("Default");
        account.setCreatedOn(LocalDate.now());
        account.setBalance(BigDecimal.valueOf(0.00));
        accountService.save(account);
    }
}