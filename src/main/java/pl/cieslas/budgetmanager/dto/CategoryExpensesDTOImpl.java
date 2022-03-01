package pl.cieslas.budgetmanager.dto;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class CategoryExpensesDTOImpl implements CategoryExpensesDTOService {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;

    public CategoryExpensesDTOImpl(ExpenseService expenseService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
    }

    @Override
    public CategoryExpensesDTO getCategoryExpensesDTO(User user, LocalDate monthStart, LocalDate now, long id) {

        Optional<Category> category = categoryService.findByIdAndUser(id, user);
        if (category.isPresent()) {

            List<Expense> allByCategoryAndUser = expenseService.findAllByCategoryAndUser
                    (category.get(), user);
            BigDecimal categorySum = expenseService.sumOfExpenses
                    (allByCategoryAndUser);
            BigDecimal monthSum = expenseService.sumOfExpenses
                    (expenseService.findAllByCategoryAndUserAndCreatedOnBetween(
                            category.get(), user, monthStart, now));

            return new CategoryExpensesDTO(allByCategoryAndUser, categorySum, monthSum);
        } else throw new RuntimeException("Not found");
    }
}
