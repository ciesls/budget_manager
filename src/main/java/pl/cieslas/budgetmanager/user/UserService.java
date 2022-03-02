package pl.cieslas.budgetmanager.user;

import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.category.Category;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface UserService {
    User findByUserName(String name);

    void saveUser(User user);

    void addDefaults(User user);

}


