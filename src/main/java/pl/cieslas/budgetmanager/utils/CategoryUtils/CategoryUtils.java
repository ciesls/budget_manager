package pl.cieslas.budgetmanager.utils.CategoryUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.utils.BudgetUtils.BudgetUtils;
import pl.cieslas.budgetmanager.utils.ExpenseUtils.ExpenseUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CategoryUtils {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final ExpenseUtils expenseUtils;
    private final BudgetService budgetService;

    public CategoryUtils(ExpenseService expenseService, CategoryService categoryService, ExpenseUtils expenseUtils,
                         BudgetService budgetService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.expenseUtils = expenseUtils;
        this.budgetService = budgetService;
    }

    public Map<Category, BigDecimal> getCategorySum(User user, List<Category> categories,
                                                    LocalDate startTIme, LocalDate endTime) {

        Map<Category, BigDecimal> categorySumMap = new HashMap<>();

        categories = categoryService.findAllByUser(user);
        for (int i = 0; i < categories.size(); i++) {
            BigDecimal categorySum = expenseUtils.sumOfExpenses(expenseService.
                    findAllByCategoryAndUserAndCreatedOnBetween(categories.get(i), user, startTIme, endTime));
            categorySumMap.put(categories.get(i), categorySum);
        }

        Map<Category, BigDecimal> categorySumMapSorted = categorySumMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return categorySumMapSorted;
    }

//    public void checkIfCategoryOtherExists(User user) {
//        Category categoryOther = categoryService.findByNameAndUser("Other", user);
//        if (categoryOther == null) {
//            Category newCategory = new Category();
//            newCategory.setUser(user);
//            newCategory.setName("Other");
//            newCategory.setBudget(budgetService.findByNameAndUser("Other", user));
//            categoryService.saveCategory(newCategory);
//
//        }
//    }

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
