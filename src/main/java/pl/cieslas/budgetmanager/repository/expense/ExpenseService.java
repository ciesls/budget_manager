package pl.cieslas.budgetmanager.repository.expense;

import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;

import java.time.LocalDate;
import java.util.List;
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


}
