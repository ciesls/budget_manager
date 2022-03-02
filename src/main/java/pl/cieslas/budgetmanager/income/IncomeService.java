package pl.cieslas.budgetmanager.income;

import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.income.Income;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeService {

    Optional<Income> findById(long id);

    Optional<Income> findByIdAndUser(Long id, User user);

    List<Income> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    Income save(Income income);

    List<Income> findAllByUserAndCreatedOnBetween(User user, LocalDate monthStart, LocalDate currentTime);

    BigDecimal sumOfIncome(List<Income> incomes);

    void updateAccountAfterIncomeAdd(User user, Income income);

    void updateAccountAfterIncomeDelete(User user, Income income);
}
