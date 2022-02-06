package pl.cieslas.budgetmanager.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findById(Long id);

    Optional<Budget> findByUserAndIdOrderByAmountDesc(User user, Long id);

    List<Budget> findAllByUser(User user);

    void deleteById(Long id);

    Budget save(Budget budget);

    Budget findByNameAndUser(String name, User user);
}
