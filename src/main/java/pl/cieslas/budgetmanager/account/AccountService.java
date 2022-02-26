package pl.cieslas.budgetmanager.account;

import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(long id);

    Optional<Account> findByIdAndUser(Long id, User user);

    List<Account> findAllByUser(User user);

    void deleteByIdAndUser(Long id, User user);

    Account save(Account Account);

    BigDecimal sumOfAccounts(List<Account> accounts);

    void transfer(BigDecimal amount, long accountID1, long accountID2, User user);

    void updateAccountWithAmount(Account orgAccount, BigDecimal orgAmount,
                                 BigDecimal orgAccBalance, BigDecimal updatedAmount,
                                 Account updatedAccount, BigDecimal updatedAccBalance);

}
