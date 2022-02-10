package pl.cieslas.budgetmanager.utils.AccountUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Account;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountUtils {

    public BigDecimal sumOfAccounts(List<Account> accounts) {
        BigDecimal sum = accounts.stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;

    }


}
