package pl.cieslas.budgetmanager.repository.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.entity.Account;
import pl.cieslas.budgetmanager.entity.User;

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
}
