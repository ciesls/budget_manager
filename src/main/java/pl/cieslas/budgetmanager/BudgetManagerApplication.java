package pl.cieslas.budgetmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.DefaultConversionService;
import pl.cieslas.budgetmanager.converters.BudgetConverter;
import pl.cieslas.budgetmanager.converters.CategoryConverter;

@SpringBootApplication
public class BudgetManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetManagerApplication.class, args);

        ConversionService conversionService = DefaultConversionService.getSharedInstance();
        ConverterRegistry converterRegistry = (ConverterRegistry) conversionService;
        converterRegistry.addConverter(new CategoryConverter());
        converterRegistry.addConverter(new BudgetConverter());

    }

}
