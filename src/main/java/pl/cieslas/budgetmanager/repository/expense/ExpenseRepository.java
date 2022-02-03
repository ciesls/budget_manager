package pl.cieslas.budgetmanager.repository.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findById(Long id);

    Optional<Expense> findByIdAndUser(Long id, User user);

    List<Expense> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    Expense save(Expense expense);

    List<Expense> findAllByCategoryAndUser(Category category, User user);

    //    get expenses from current month
    List<Expense> findAllByUserAndCreatedOnBetween(User user, LocalDate monthStart, LocalDate currentTime);

    //    get last 5 expenses
//    @Query(value = "select * from expenses ")
    List<Expense> findTop5ByUserOrderByIdDesc(User user);


    List<Expense> findAllByCategoryAndUserAndCreatedOnBetween(Category category, User user, LocalDate monthStart, LocalDate currentTime);







}
