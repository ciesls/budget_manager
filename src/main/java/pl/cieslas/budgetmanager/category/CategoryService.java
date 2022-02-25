package pl.cieslas.budgetmanager.category;

import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> getCategoryPerUser(Long id, User user);

    List<Category> findAllByUser(User user);

    void deleteById(Long id);

    void saveCategory(Category category);

    void update(Category category);

    Optional<Category> findById(Long id);

    Category findByNameAndUser(String name, User user);

    List<Category> findAllByUserAndBudget(User user, Budget budget);

    Map<Category, BigDecimal> getCategorySum(User user, List<Category> categories,
                                             LocalDate startTIme, LocalDate endTime);


}
