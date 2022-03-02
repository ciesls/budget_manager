package pl.cieslas.budgetmanager.dto;

import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.category.Category;

import java.math.BigDecimal;
import java.util.List;

public class BudgetDetailsDTO {

    private final List<Category> categories;
    private final Budget budget;
    private final BigDecimal budgetSum;

    public BudgetDetailsDTO(List<Category> categories, Budget budget, BigDecimal budgetSum) {
        this.categories = categories;
        this.budget = budget;
        this.budgetSum = budgetSum;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public BigDecimal getBudgetSum() {
        return budgetSum;
    }

    public Budget getBudget() {
        return budget;
    }
}
