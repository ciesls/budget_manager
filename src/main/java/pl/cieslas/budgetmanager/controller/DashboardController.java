package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.security.CurrentUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private BudgetService budgetService;

    public DashboardController(ExpenseService expenseService, CategoryService categoryService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.budgetService = budgetService;
    }


    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter formatDate() {
        DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return localDateTimeFormat;
    }

    @ModelAttribute("categories")
    public List<Category> getCategories(@AuthenticationPrincipal CurrentUser currentUser) {
        return categoryService.findAllByUser(currentUser.getUser());
    }

    @ModelAttribute("currentMonthExpenses")
    public List<Expense> currentMonthExpenses(LocalDate startTime, LocalDate now, @AuthenticationPrincipal CurrentUser currentUser) {
        startTime = LocalDate.now().withDayOfMonth(1);
        now = LocalDate.now();
        return expenseService.findAllByUserAndCreatedOnBetween(currentUser.getUser(), startTime, now);

    }

    @ModelAttribute("last5Expenses")
    public List<Expense> getCurrentMonthExpenses(@AuthenticationPrincipal CurrentUser currentUser) {
        return expenseService.findTop5ByUserOrderByIdDesc(currentUser.getUser());
    }

    @ModelAttribute("budgets")
    public List<Budget> getBudgets(@AuthenticationPrincipal CurrentUser currentUser) {
        return budgetService.findAllByUser(currentUser.getUser());
    }

    @GetMapping
    public String showDashboard(){
        return "dashboard";
    }



}
