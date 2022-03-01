package pl.cieslas.budgetmanager.dto;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.budget.BudgetService;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class BudgetDetailsServiceImpl implements BudgetDetailsDTOService {

    private final BudgetService budgetService;
    private final CategoryService categoryService;

    public BudgetDetailsServiceImpl(BudgetService budgetService, CategoryService categoryService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }


    @Override
    public BudgetDetailsDTO getBudgetDetailsDTO(User user, LocalDate startTime, LocalDate endTime, long id) {

        Optional<Budget> budget = budgetService.findByUserAndIdOrderByAmountDesc(user, id);
        if (budget.isPresent()) {
            List<Category> budgetCategories = categoryService.findAllByUserAndBudget(user, budget.get());
            BigDecimal budgetSum = budgetService.calculateExpensesInBudgetDates(budgetCategories, user, startTime, endTime);
            return new BudgetDetailsDTO(budgetCategories, budget.get(), budgetSum);
        } else throw new RuntimeException("Not found");

    }
}
