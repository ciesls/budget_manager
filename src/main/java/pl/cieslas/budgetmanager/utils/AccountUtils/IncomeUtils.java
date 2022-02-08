package pl.cieslas.budgetmanager.utils.AccountUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.repository.income.IncomeService;

@Component
public class IncomeUtils {

    private final IncomeService incomeService;

    public IncomeUtils(IncomeService incomeService) {
        this.incomeService = incomeService;
    }



}
