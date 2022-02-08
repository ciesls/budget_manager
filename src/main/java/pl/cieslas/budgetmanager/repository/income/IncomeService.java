package pl.cieslas.budgetmanager.repository.income;

import pl.cieslas.budgetmanager.entity.Income;
import pl.cieslas.budgetmanager.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

public interface IncomeService {

    Optional<Income> findById(long id);

    Optional<Income> findByIdAndUser(Long id, User user);

    List<Income> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    Income save(Income income);


}
