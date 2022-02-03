package pl.cieslas.budgetmanager.utils.BudgetUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.utils.ExpenseUtils.ExpenseUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BudgetUtils {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final ExpenseUtils expenseUtils;

    public BudgetUtils(ExpenseService expenseService, CategoryService categoryService, ExpenseUtils expenseUtils) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.expenseUtils = expenseUtils;
    }

    public BigDecimal calculateExpensesInBudget(List<Category> categories, User currentUser, LocalDate monthStart, LocalDate currentTime) {

// get categories in budget, iterate and calculate sum of expenses in give list

        BigDecimal categorySum = BigDecimal.ZERO;

        for (int i = 0; i < categories.size(); i++) {
            List<Expense> expensesCategory = expenseService.findAllByCategoryAndUserAndCreatedOnBetween(categories.get(i), currentUser, monthStart, currentTime);
            categorySum = categorySum.add(expenseUtils.sumOfExpenses(expensesCategory));
        }
        return categorySum;
    }


    public List<Expense> getBudgetExpenses(List<Category> categories, User user) {

        List<Expense> allBudgetExpenses = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            allBudgetExpenses.addAll(expenseService.findAllByCategoryAndUser(categories.get(i), user));
        }
        return allBudgetExpenses;

    }
}
