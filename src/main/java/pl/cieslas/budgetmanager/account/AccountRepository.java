package pl.cieslas.budgetmanager.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cieslas.budgetmanager.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(long id);

    Optional<Account> findByIdAndUser(Long id, User user);

    List<Account> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    Account save(Account Account);

}
