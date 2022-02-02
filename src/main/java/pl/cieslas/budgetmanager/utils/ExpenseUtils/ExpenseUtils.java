package pl.cieslas.budgetmanager.utils.ExpenseUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExpenseUtils {

    private final CategoryService categoryService;
    private final ExpenseService expenseService;

    public ExpenseUtils(CategoryService categoryService, ExpenseService expenseService) {
        this.categoryService = categoryService;
        this.expenseService = expenseService;
    }

    //  calculate sum of expenses in a given list
    public BigDecimal sumOfExpenses(List<Expense> expenses) {
        BigDecimal sum = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;

    }

    public Map<YearMonth, List<Expense>> groupExpensesByMonth(List<Expense> expenses) {

        BigDecimal sumPerMonthCategory = BigDecimal.ZERO;

        Map<YearMonth, List<Expense>> groupedByMonth = expenses.stream().collect(Collectors.groupingBy(Expense::getYearMonth));
        Map<YearMonth, BigDecimal> sumPerMonthYear;

        for (List<Expense> groupedExpenses : groupedByMonth.values()) {
            sumPerMonthCategory = sumPerMonthCategory.add(sumOfExpenses(groupedExpenses));
//            System.out.println(sumPerMonthCategory);
        }



        return groupedByMonth;

    }


}
