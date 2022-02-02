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
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

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

        BigDecimal categorySum = BigDecimal.ZERO;

        for (int i = 0; i < categories.size(); i++) {
            List<Expense> expensesCategory = expenseService.findAllByCategoryAndUserAndCreatedOnBetween(categories.get(i), currentUser, monthStart, currentTime);
            categorySum = categorySum.add(expenseUtils.sumOfExpenses(expensesCategory));
        }
        return categorySum;
    }

    public Map<YearMonth, List<Expense>> groupExpensesByMonth(List<Expense> expenses) {
        Map<YearMonth, List<Expense>> groupedByMonth = expenses.stream().collect(Collectors.groupingBy(Expense::getYearMonth));
        return groupedByMonth;

    }


}
