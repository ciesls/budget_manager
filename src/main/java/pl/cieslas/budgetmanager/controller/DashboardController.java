package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cieslas.budgetmanager.entity.Account;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.account.AccountService;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.utils.BudgetUtils.BudgetUtils;
import pl.cieslas.budgetmanager.utils.CategoryUtils.CategoryUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final BudgetUtils budgetUtils;
    private final CategoryUtils categoryUtils;
    private final AccountService accountService;

    public DashboardController(ExpenseService expenseService, CategoryService categoryService, BudgetService budgetService, BudgetUtils budgetUtils, CategoryUtils categoryUtils, AccountService accountService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.budgetUtils = budgetUtils;
        this.categoryUtils = categoryUtils;
        this.accountService = accountService;
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

//    add budgets with amounts in a month
    @ModelAttribute("budgetAmount")
    public Map<Budget, BigDecimal> getBudgetSum(@AuthenticationPrincipal CurrentUser currentUser) {
        LocalDate startTime = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        List<Budget> budgets = budgetService.findAllByUser(currentUser.getUser());
        Map<Budget, BigDecimal> budgetAmount = new HashMap<>();
        for (int i = 0; i < budgets.size(); i++) {
            List<Category> budgetCategories = categoryService.findAllByUserAndBudget(currentUser.getUser(), (budgets.get(i)));
            BigDecimal budgetSum = budgetUtils.calculateExpensesInBudgetDates(budgetCategories, currentUser.getUser(), startTime, now);
            budgetAmount.put(budgets.get(i), budgetSum);
        }

        return budgetAmount;
    }

//    add categories with sums
    @ModelAttribute("categoriesSum")
    public Map<Category, BigDecimal> getCategoriesSums(@AuthenticationPrincipal CurrentUser currentUser) {
        List<Category> categories = categoryService.findAllByUser(currentUser.getUser());
        LocalDate startTime = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        return categoryUtils.getCategorySum(currentUser.getUser(), categories, startTime, now);
    }

    @ModelAttribute("accounts")
    public List<Account> accounts(@AuthenticationPrincipal CurrentUser currentUser) {
        return accountService.findAllByUser(currentUser.getUser());
    }


    @GetMapping
    public String showDashboard(){
        return "dashboard";
    }




}
