package pl.cieslas.budgetmanager.repository.budget;

import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface BudgetService {

    Optional<Budget> get(Long id);

    Optional<Budget> getPerUser(Long id, User user);

    List<Budget> findAllByUser(User user);

    void deleteById(Long id);

    void saveBudget(Budget budget);

    void update(Budget budget);



}
