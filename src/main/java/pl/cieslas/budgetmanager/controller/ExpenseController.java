package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.entity.Account;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.account.AccountService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.security.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    public ExpenseController(ExpenseService expenseService, UserService userService, CategoryService categoryService, AccountService accountService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @ModelAttribute("categories")
    public List<Category> categories(@AuthenticationPrincipal CurrentUser customUser) {
        return categoryService.findAllByUser(customUser.getUser());
    }

    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter formatDate() {
        DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return localDateTimeFormat;
    }

    @ModelAttribute("accounts")
    public List<Account> accounts(@AuthenticationPrincipal CurrentUser currentUser) {
        return accountService.findAllByUser(currentUser.getUser());
    }

    @GetMapping("/all")
    public String getAllUserExpenses(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("expenses", expenseService.findAllByUser(customUser.getUser()));
        return "expense/userExpenses";
    }

    @GetMapping("/details/{id}")
    public String getExpense(@AuthenticationPrincipal CurrentUser customUser, @PathVariable long id, Model model) {
        Optional<Expense> expense = expenseService.getPerUser(id, customUser.getUser());
        if (expense.isPresent()) {
            model.addAttribute("expense", expense.get());
        }
        return "expense/userExpensesDetails";
    }

    //  show expense form
    @GetMapping("/add")
    public String addExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "expense/expenseAddForm";
    }

    @PostMapping("/add")
    public String addExpense(@AuthenticationPrincipal CurrentUser customUser, @Valid Expense expense, BindingResult result) {
        if (result.hasErrors()) {
            return "expense/expenseAddForm";
        }
        expense.setUser(customUser.getUser());
        expense.setCreatedOn(LocalDate.now());
        expenseService.saveExpense(expense);

        Long accountId = expense.getAccount().getId();
        Optional<Account> account = accountService.findById(accountId);
        if (account.isPresent()){
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.subtract(expense.getAmount()));
            accountService.save(account.get());
        }

        return "redirect:/expenses/all";
    }

    //    show form for editing expense
    @GetMapping("/edit/{id}")
    public String editExpenseForm(Model model, @PathVariable long id) {
        model.addAttribute("expense", expenseService.findById(id));
        return "expense/expenseEditForm";
    }

    //    edit expense
    @PostMapping("/edit/{id}")
    public String editExpense(Expense expense, @AuthenticationPrincipal CurrentUser customUser) {
        expense.setUser(customUser.getUser());
        expenseService.saveExpense(expense);

        Long accountId = expense.getAccount().getId();
        Optional<Account> account = accountService.findById(accountId);
        if (account.isPresent()){
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.add(expense.getAmount()));
            accountService.save(account.get());
        }

        return "redirect:/expenses/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable long id, @AuthenticationPrincipal CurrentUser customUser) {
        expenseService.deleteByIdAndUser(id, customUser.getUser());
        return "redirect:/expenses/all";
    }


}
