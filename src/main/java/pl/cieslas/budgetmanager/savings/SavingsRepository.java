package pl.cieslas.budgetmanager.savings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cieslas.budgetmanager.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    Optional<Savings> findById(Long id);

    Optional<Savings> findByIdAndUser(Long id, User user);

    List<Savings> findAllByUser(User user);

    Savings save(Savings savings);

    void deleteByIdAndUser(Long id, User user);

}
