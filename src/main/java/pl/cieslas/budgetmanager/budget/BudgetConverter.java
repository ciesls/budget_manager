package pl.cieslas.budgetmanager.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BudgetConverter implements Converter<String, Budget> {

    @Autowired
    private BudgetService budgetService;

    @Override
    public Budget convert(String source) {
        return budgetService.findById(Long.parseLong(source)).get();
    }


}
