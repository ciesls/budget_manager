package pl.cieslas.budgetmanager.repository.income;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cieslas.budgetmanager.entity.Income;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    Optional<Income> findById(long id);

    Optional<Income> findByIdAndUser(Long id, User user);

    List<Income> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    Income save(Income income);

}
