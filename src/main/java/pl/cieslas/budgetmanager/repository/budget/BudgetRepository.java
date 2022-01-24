package pl.cieslas.budgetmanager.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findById(Long id);

    Optional<Budget> findByIdAndUser(Long id, User user);

    Budget save(Budget budget);

    void deleteById(Long id);

    List<Budget> findAllByUser(User user);



}
