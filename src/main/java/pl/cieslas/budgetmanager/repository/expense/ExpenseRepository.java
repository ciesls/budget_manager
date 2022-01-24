package pl.cieslas.budgetmanager.repository.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findById(Long id);

    Optional<Expense> findByIdAndUser(Long id, User user);

    void deleteById(Long id);

    List<Expense> findAllByUser(User user);

    Expense save(Expense expense);

}
