package pl.cieslas.budgetmanager.repository.expense;

import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    Optional<Expense> get(Long id);

    Optional<Expense> getPerUser(Long id, User user);

    List<Expense> findAllByUser(User user);

    void deleteById(Long id);

    void saveExpense(Expense expense);

    void add(Expense expense);

    void update(Expense expense);


}
