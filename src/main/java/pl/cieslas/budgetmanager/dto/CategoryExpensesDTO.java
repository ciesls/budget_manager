package pl.cieslas.budgetmanager.dto;

import pl.cieslas.budgetmanager.expense.Expense;

import java.math.BigDecimal;
import java.util.List;


public class CategoryExpensesDTO {

    private final List<Expense> expensesCategories;
    private final BigDecimal categorySum;
    private final BigDecimal monthSum;

    public CategoryExpensesDTO(List<Expense> expensesCategories, BigDecimal categorySum, BigDecimal monthSum) {
        this.expensesCategories = expensesCategories;
        this.categorySum = categorySum;
        this.monthSum = monthSum;
    }

    public List<Expense> getExpensesCategories() {
        return expensesCategories;
    }

    public BigDecimal getCategorySum() {
        return categorySum;
    }

    public BigDecimal getMonthSum() {
        return monthSum;
    }
}
