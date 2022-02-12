package pl.cieslas.budgetmanager.utils.AccountUtils;

import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Income;
import pl.cieslas.budgetmanager.repository.income.IncomeService;

import java.math.BigDecimal;
import java.util.List;

@Component
public class IncomeUtils {

    private final IncomeService incomeService;

    public IncomeUtils(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    public BigDecimal sumOfIncome(List<Income> incomes) {
        BigDecimal sum = incomes.stream().map(Income::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum;

    }

}
