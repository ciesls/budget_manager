package pl.cieslas.budgetmanager.repository.account;

import pl.cieslas.budgetmanager.entity.Account;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(long id);

    Optional<Account> findByIdAndUser(Long id, User user);

    List<Account> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    Account save(Account Account);


}
