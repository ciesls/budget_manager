package pl.cieslas.budgetmanager.repository.savings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cieslas.budgetmanager.entity.Savings;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    Optional<Savings> findById(Long id);

    Optional<Savings> findByIdAndUser(Long id, User user);

    List<Savings> findAllByUser(User user);

    Savings save(Savings savings);

    void deleteById(Long id);

}
