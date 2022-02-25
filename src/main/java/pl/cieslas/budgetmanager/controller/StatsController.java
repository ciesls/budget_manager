package pl.cieslas.budgetmanager.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.budget.BudgetService;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.user.CurrentUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/stats")
@Controller
public class StatsController {

    private final CategoryService categoryService;
    private final ExpenseService expenseService;
    private final BudgetService budgetService;


    public StatsController(CategoryService categoryService, ExpenseService expenseService,
                           BudgetService budgetService) {
        this.categoryService = categoryService;
        this.expenseService = expenseService;
        this.budgetService = budgetService;
    }

    @ModelAttribute("budgets")
    public List<Budget> budgets(@AuthenticationPrincipal CurrentUser currentUser) {
        return budgetService.findAllByUser(currentUser.getUser());
    }

    @ModelAttribute("categories")
    public List<Category> categories(@AuthenticationPrincipal CurrentUser currentUser) {
        return categoryService.findAllByUser(currentUser.getUser());
    }

    @GetMapping("/categoriesStatsForm")
    public String categoriesStatsForm(){
        return "stats/categoriesStatsForm";
    }

    @PostMapping("/categoriesStats")
    public String categoryStats(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                @AuthenticationPrincipal CurrentUser currentUser,
                                @RequestParam("category") long categoryID, RedirectAttributes redirectAttributes) {

// find category expenses in given period, group them and pass them to categoryStats view
        Optional<Category> category = categoryService.findById(categoryID);
        List<Expense> expenses = expenseService.findAllByCategoryAndUserAndCreatedOnBetween(category.get(),
                currentUser.getUser(), startDate, endDate);
        redirectAttributes.addFlashAttribute("category", category.get());
        redirectAttributes.addFlashAttribute("groupedByCategory", expenseService.groupExpensesByMonth(expenses));

        return "redirect:/stats/categoriesStats";
    }

    // view of categories expenses grouped by month in selected period
    @GetMapping("/categoriesStats")
    public String showCategoriesStats() {
        return "stats/categoriesStats";
    }

    @GetMapping("/budgetStatsForm")
    public String budgetStatsForm(){
        return "stats/budgetStatsForm";
    }

    @PostMapping("/budgetStats")
    public String budgetStats(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                              @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                              @AuthenticationPrincipal CurrentUser currentUser,
                              @RequestParam("budget") long budgetID, RedirectAttributes redirectAttributes) {

        Optional<Budget> budget = budgetService.findById(budgetID);
        List<Category> categoriesBudget = categoryService.findAllByUserAndBudget(currentUser.getUser(), budget.get());
        List<Expense> expensesBudget = budgetService.getBudgetExpensesDates(categoriesBudget,
                currentUser.getUser(), startDate, endDate);
        redirectAttributes.addFlashAttribute("expensesGrouped",
                expenseService.groupExpensesByMonth(expensesBudget));
        redirectAttributes.addFlashAttribute("budget", budget.get());
        return "redirect:/stats/budgetStats";

    }

    @GetMapping("/budgetStats")
    public String showBudgetStats() {
        return "stats/budgetStats";
    }
}
