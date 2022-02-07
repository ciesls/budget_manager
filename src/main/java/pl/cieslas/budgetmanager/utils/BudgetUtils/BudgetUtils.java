package pl.cieslas.budgetmanager.utils.BudgetUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.utils.CategoryUtils.CategoryUtils;
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
    private final BudgetService budgetService;

    public BudgetUtils(ExpenseService expenseService, CategoryService categoryService, ExpenseUtils expenseUtils,
                       BudgetService budgetService, CategoryUtils categoryUtils) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.expenseUtils = expenseUtils;
        this.budgetService = budgetService;
    }

    public BigDecimal calculateExpensesInBudgetDates(List<Category> categories, User currentUser,
                                                     LocalDate monthStart, LocalDate currentTime) {

// get categories in budget, iterate and calculate sum of expenses in give list

        BigDecimal categorySum = BigDecimal.ZERO;

        for (int i = 0; i < categories.size(); i++) {
            List<Expense> expensesCategory = expenseService.findAllByCategoryAndUserAndCreatedOnBetween
                    (categories.get(i), currentUser, monthStart, currentTime);
            categorySum = categorySum.add(expenseUtils.sumOfExpenses(expensesCategory));
        }
        return categorySum;
    }


    public BigDecimal calculateExpensesInBudget(List<Category> categories, User currentUser) {

// get categories in budget, iterate and calculate sum of expenses in give list

        BigDecimal categorySum = BigDecimal.ZERO;

        for (int i = 0; i < categories.size(); i++) {
            List<Expense> expensesCategory = expenseService.findAllByCategoryAndUser(categories.get(i), currentUser);
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

    public List<Expense> getBudgetExpensesDates(List<Category> categories, User user, LocalDate startTime, LocalDate endTime) {

        List<Expense> allBudgetExpenses = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            allBudgetExpenses.addAll(expenseService.findAllByCategoryAndUserAndCreatedOnBetween(categories.get(i), user, startTime, endTime));
        }
        return allBudgetExpenses;

    }


//    public void checkIfBudgetOtherExists(User user) {
//
//        Budget budgetOther = budgetService.findByNameAndUser("Other", user);
//        if (budgetOther == null) {
//            Budget newBudget = new Budget();
//            newBudget.setName("Other");
//            newBudget.setAmount(BigDecimal.ZERO);
//            newBudget.setUser(user);
//            budgetService.saveBudget(newBudget);
//        }
//
//    }

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


}
