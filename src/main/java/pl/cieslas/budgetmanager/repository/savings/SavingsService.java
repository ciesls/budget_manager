package pl.cieslas.budgetmanager.repository.savings;

import pl.cieslas.budgetmanager.entity.Savings;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface SavingsService {

    Optional<Savings> findById(Long id);

    Optional<Savings> findByIdAndUser(Long id, User user);

    List<Savings> findAllByUser(User user);

    Savings save(Savings savings);

    void deleteById(Long id);


}
