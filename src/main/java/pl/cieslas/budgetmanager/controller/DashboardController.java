package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.account.AccountService;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.budget.BudgetService;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.income.Income;
import pl.cieslas.budgetmanager.income.IncomeService;
import pl.cieslas.budgetmanager.user.CurrentUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final AccountService accountService;
    private final IncomeService incomeService;

    public DashboardController(ExpenseService expenseService, CategoryService categoryService,
                               BudgetService budgetService, AccountService accountService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.accountService = accountService;
        this.incomeService = incomeService;
    }

    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter formatDate() {
        DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return localDateTimeFormat;
    }

    @ModelAttribute("categories")
    public List<Category> getCategories(@AuthenticationPrincipal CurrentUser currentUser) {
        return categoryService.findAllByUser(currentUser.getUser());
    }

    @ModelAttribute("currentMonthExpenses")
    public List<Expense> currentMonthExpenses(@AuthenticationPrincipal CurrentUser currentUser) {
        LocalDate startTime = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        return expenseService.findAllByUserAndCreatedOnBetween(currentUser.getUser(), startTime, now);
    }

    @ModelAttribute("currentMonthExpensesSum")
    public BigDecimal currentMonthExpensesSum(@AuthenticationPrincipal CurrentUser currentUser) {
        LocalDate startTime = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        List<Expense> expenses = expenseService.findAllByUserAndCreatedOnBetween(currentUser.getUser(), startTime, now);
        return expenseService.sumOfExpenses(expenses);
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
        return budgetService.getBudgetSum(currentUser.getUser(), budgets, startTime, now);
    }

    //    add categories with sums
    @ModelAttribute("categoriesSum")
    public Map<Category, BigDecimal> getCategoriesSums(@AuthenticationPrincipal CurrentUser currentUser) {
        List<Category> categories = categoryService.findAllByUser(currentUser.getUser());
        LocalDate startTime = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        return categoryService.getCategorySum(currentUser.getUser(), categories, startTime, now);
    }

    @ModelAttribute("accounts")
    public List<Account> accounts(@AuthenticationPrincipal CurrentUser currentUser) {
        return accountService.findAllByUser(currentUser.getUser());
    }

    @ModelAttribute("balanceSum")
    public BigDecimal balanceSum(@AuthenticationPrincipal CurrentUser currentUser) {
        List<Account> accounts = accountService.findAllByUser(currentUser.getUser());
        return accountService.sumOfAccounts(accounts);
    }

    @ModelAttribute("currentMonthIncome")
    public BigDecimal currentMonthIncomeSum(@AuthenticationPrincipal CurrentUser currentUser) {
        LocalDate startTime = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        List<Income> incomes = incomeService.findAllByUserAndCreatedOnBetween(currentUser.getUser(), startTime, now);
        return incomeService.sumOfIncome(incomes);
    }

    @GetMapping
    public String showDashboard() {
        return "dashboard";
    }

}
