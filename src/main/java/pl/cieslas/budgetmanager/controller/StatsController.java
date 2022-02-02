package pl.cieslas.budgetmanager.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.utils.ExpenseUtils.ExpenseUtils;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/stats")
@Controller
public class StatsController {

    private final CategoryService categoryService;
    private final ExpenseUtils expenseUtils;
    private final ExpenseService expenseService;

    public StatsController(CategoryService categoryService, ExpenseUtils expenseUtils, ExpenseService expenseService) {
        this.categoryService = categoryService;
        this.expenseUtils = expenseUtils;
        this.expenseService = expenseService;
    }

    @PostMapping("/categoriesMonth")
    public String categoryStats(@RequestParam("startDate") @DateTimeFormat(pattern = "dd/mm/yyyy") LocalDate startDate,
                                @RequestParam("endTime") @DateTimeFormat(pattern = "dd/mm/YYYY") LocalDate endDate,
                                @AuthenticationPrincipal CurrentUser currentUser,
                                @RequestParam Category category) {

//        List<Expense> expenses = expenseService.findAllByCategoryAndUserAndCreatedOnBetween(category, currentUser.getUser(), startDate, endDate);


        return "";
    }


}
