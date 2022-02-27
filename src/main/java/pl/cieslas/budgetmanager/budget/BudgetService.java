package pl.cieslas.budgetmanager.budget;

import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.user.CurrentUser;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BudgetService {

    Optional<Budget> findById(Long id);

    Optional<Budget> findByIdAndUser(Long id, User user);

    Optional<Budget> findByUserAndIdOrderByAmountDesc(User user, Long id);

    List<Budget> findAllByUser(User user);

    void deleteById(Long id);

    void saveBudget(Budget budget);

    void update(Budget budget);

    Budget findByNameAndUser(String name, User user);

    BigDecimal calculateExpensesInBudgetDates(List<Category> categories, User currentUser,
                                              LocalDate monthStart, LocalDate currentTime);

    BigDecimal calculateExpensesInBudget(List<Category> categories, User currentUser);

    List<Expense> getBudgetExpenses(List<Category> categories, User user);

    List<Expense> getBudgetExpensesDates(List<Category> categories, User user, LocalDate startTime, LocalDate endTime);

    Map<Budget, BigDecimal> getBudgetSum(User user, List<Budget> budgets, LocalDate startTime, LocalDate now);
}
