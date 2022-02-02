package pl.cieslas.budgetmanager.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;

@Component
public class BudgetConverter implements Converter<String, Budget> {

    @Autowired
    private BudgetService budgetService;

    @Override
    public Budget convert(String source) {
        return budgetService.findById(Long.parseLong(source)).get();
    }


}
