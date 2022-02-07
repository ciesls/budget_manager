package pl.cieslas.budgetmanager.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.utils.BudgetUtils.BudgetUtils;
import pl.cieslas.budgetmanager.utils.ExpenseUtils.ExpenseUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/stats")
@Controller
public class StatsController {

    private final CategoryService categoryService;
    private final ExpenseUtils expenseUtils;
    private final ExpenseService expenseService;
    private final BudgetService budgetService;
    private final BudgetUtils budgetUtils;


    public StatsController(CategoryService categoryService, ExpenseUtils expenseUtils, ExpenseService expenseService,
                           BudgetService budgetService, BudgetUtils budgetUtils) {
        this.categoryService = categoryService;
        this.expenseUtils = expenseUtils;
        this.expenseService = expenseService;
        this.budgetService = budgetService;
        this.budgetUtils = budgetUtils;
    }

    @ModelAttribute("budgets")
    public List<Budget> budgets(@AuthenticationPrincipal CurrentUser customUser) {
        return budgetService.findAllByUser(customUser.getUser());
    }

    @ModelAttribute("categories")
    public List<Category> categories(@AuthenticationPrincipal CurrentUser customUser) {
        return categoryService.findAllByUser(customUser.getUser());
    }

    @PostMapping("/categoryStats")
    public String categoryStats(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                @AuthenticationPrincipal CurrentUser currentUser,
                                @RequestParam("category") long categoryID, RedirectAttributes redirectAttributes) {

// find category expenses in given period, group them and pass them to categoryStats view
        Optional<Category> category = categoryService.findById(categoryID);
        List<Expense> expenses = expenseService.findAllByCategoryAndUserAndCreatedOnBetween(category.get(),
                currentUser.getUser(), startDate, endDate);
        redirectAttributes.addFlashAttribute("category", category.get());
        redirectAttributes.addFlashAttribute("groupedByCategory", expenseUtils.groupExpensesByMonth(expenses));

        return "redirect:/stats/categoryStats";
    }

    // view of categories expenses grouped by month in selected period
    @GetMapping("/categoryStats")
    public String showCategoriesStats() {
        return "stats/categoriesStats";
    }

    @GetMapping("/budgetStatsForm")
    public String budgetStatsForm(){
        return "stats/budgetStatsForm";
    }


    @GetMapping("/categoriesStatsForm")
    public String categoriesStatsForm(){
        return "stats/categoriesStatsForm";
    }


    @PostMapping("/budgetStats")
    public String budgetStats(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                              @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                              @AuthenticationPrincipal CurrentUser currentUser,
                              @RequestParam("budget") long budgetID, RedirectAttributes redirectAttributes) {

        Optional<Budget> budget = budgetService.findById(budgetID);
        List<Category> categoriesBudget = categoryService.findAllByUserAndBudget(currentUser.getUser(), budget.get());
        List<Expense> expensesBudget = budgetUtils.getBudgetExpensesDates(categoriesBudget, currentUser.getUser(), startDate, endDate);
        redirectAttributes.addFlashAttribute("expensesGrouped", expenseUtils.groupExpensesByMonth(expensesBudget));
        redirectAttributes.addFlashAttribute("budget", budget.get());
        return "redirect:/stats/budgetStats";

    }

    @GetMapping("/budgetStats")
    public String showBudgetStats() {
        return "stats/budgetStats";
    }
}
