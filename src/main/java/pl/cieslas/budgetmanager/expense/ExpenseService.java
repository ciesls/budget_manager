package pl.cieslas.budgetmanager.expense;

import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpenseService {

    Optional<Expense> findById(Long id);

    Optional<Expense> getPerUser(Long id, User user);

    List<Expense> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    void saveExpense(Expense expense);

    void update(Expense expense);

    List<Expense> findAllByCategoryAndUser(Category category, User user);

    //    get expenses from current month
    List<Expense> findAllByUserAndCreatedOnBetween(User user, LocalDate monthStart, LocalDate currentTime);


    List<Expense> findTop5ByUserOrderByIdDesc(User user);

    List<Expense> findAllByCategoryAndUserAndCreatedOnBetween(Category category, User user, LocalDate monthStart, LocalDate currentTime);

    BigDecimal sumOfExpenses(List<Expense> expenses);

    Map<YearMonth, BigDecimal> groupExpensesByMonth(List<Expense> expenses);

}
