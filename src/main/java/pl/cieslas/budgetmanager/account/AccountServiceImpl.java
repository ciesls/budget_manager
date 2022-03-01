package pl.cieslas.budgetmanager.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findById(long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> findByIdAndUser(Long id, User user) {
        return accountRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Account> findAllByUser(User user) {
        return accountRepository.findAllByUser(user);
    }

    @Override
    public void deleteByIdAndUser(Long id, User user) {
        accountRepository.deleteByIdAndUser(id, user);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public BigDecimal sumOfAccounts(List<Account> accounts) {
        return accounts.stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void transfer(BigDecimal amount, long accountID1, long accountID2, User user) {
        Optional<Account> acc1 = accountRepository.findByIdAndUser(accountID1, user);
        Optional<Account> acc2 = accountRepository.findByIdAndUser(accountID2, user);
        if (acc1.isPresent()) {
            BigDecimal acc1Balance = acc1.get().getBalance();
            acc1.get().setBalance(acc1Balance.subtract(amount));
        } else throw new RuntimeException("Account not found");
        if (acc2.isPresent()) {
            BigDecimal acc2Balance = acc2.get().getBalance();
            acc2.get().setBalance(acc2Balance.add(amount));
        } else throw new RuntimeException("Account not found");
        accountRepository.save(acc1.get());
        accountRepository.save(acc2.get());
    }

    //    to check!!!
    @Override
    public void updateAccountWithAmountExpense(Account orgAccount, BigDecimal orgAmount, BigDecimal orgAccBalance,
                                               BigDecimal updatedAmount, Account updatedAccount,
                                               BigDecimal updatedAccBalance) {

        int result = orgAmount.compareTo(updatedAmount);

        if ((orgAccount == updatedAccount) && (result == -1)) { //orgAmount is less than updatedAmount -
            BigDecimal delta = updatedAmount.subtract(orgAmount);
            orgAccount.setBalance(orgAccBalance.subtract(delta));
            accountRepository.save(orgAccount);

        } else if ((orgAccount == updatedAccount) && (result == 1)) {
            BigDecimal delta = orgAmount.subtract(updatedAmount);
            orgAccount.setBalance(orgAccBalance.add(delta));
            accountRepository.save(orgAccount);

        } else if (orgAccount != updatedAccount) { //account change
            // subtract from orgAccount full amount, add to new account full amount
            updatedAccount.setBalance(updatedAccBalance.subtract(updatedAmount));
            accountRepository.save(updatedAccount);
            orgAccount.setBalance(orgAccBalance.add(orgAmount));
            accountRepository.save(orgAccount);
        }

    }

    @Override
    public void updateAccountWithAmountIncome(Account orgAccount, BigDecimal orgAmount, BigDecimal orgAccBalance,
                                              BigDecimal updatedAmount, Account updatedAccount,
                                              BigDecimal updatedAccBalance) {
        int result = orgAmount.compareTo(updatedAmount);

        if ((orgAccount == updatedAccount) && (result == -1)) { //if same acc && updated amt > org amt; updating only orgAccount
            BigDecimal delta = updatedAmount.subtract(orgAmount);
            orgAccount.setBalance(orgAccBalance.add(delta));
            accountRepository.save(orgAccount);

        } else if ((orgAccount == updatedAccount) && (result == 1)) { //if same acc && org amt > updated amt; updating only orgAccount
            BigDecimal delta = orgAmount.subtract(updatedAmount);
            orgAccount.setBalance(orgAccBalance.subtract(delta));
            accountRepository.save(orgAccount);

//            to be finished - how to move amounts between accounts
        } else if (orgAccount != updatedAccount) { //account change && if updated > org;
            // subtract from orgAccount full amount, add to new account full amount
            updatedAccount.setBalance(updatedAccBalance.add(updatedAmount));
            accountRepository.save(updatedAccount);
            orgAccount.setBalance(orgAccBalance.subtract(orgAmount));
            accountRepository.save(orgAccount);

        }
    }
}
