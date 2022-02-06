package pl.cieslas.budgetmanager.repository.budget;

import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface BudgetService {

    Optional<Budget> findById(Long id);

    Optional<Budget> findByUserAndIdOrderByAmountDesc(User user, Long id);

    List<Budget> findAllByUser(User user);

    void deleteById(Long id);

    void saveBudget(Budget budget);

    void update(Budget budget);

    Budget findByNameAndUser(String name, User user);
}
