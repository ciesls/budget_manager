package pl.cieslas.budgetmanager.savings;

import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SavingsService {

    Optional<Savings> findById(Long id);

    Optional<Savings> findByIdAndUser(Long id, User user);

    List<Savings> findAllByUser(User user);

    Savings save(Savings savings);

    void deleteByIdAndUser(Long id, User user);

    void increaseValue(User user, long id, BigDecimal newValue);

    Map<Savings, BigDecimal> getSavingsDetails(List<Savings> savings);


}
