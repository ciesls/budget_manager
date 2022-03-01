package pl.cieslas.budgetmanager.expense;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.account.AccountService;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.updates.UpdatesService;
import pl.cieslas.budgetmanager.user.CurrentUser;

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
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final UpdatesService updatesService;

    public ExpenseController(ExpenseService expenseService, CategoryService categoryService,
                             AccountService accountService, UpdatesService updatesService) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.updatesService = updatesService;
    }

    @ModelAttribute("categories")
    public List<Category> categories(@AuthenticationPrincipal CurrentUser currentUser) {
        return categoryService.findAllByUser(currentUser.getUser());
    }

    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter formatDate() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    @ModelAttribute("accounts")
    public List<Account> accounts(@AuthenticationPrincipal CurrentUser currentUser) {
        return accountService.findAllByUser(currentUser.getUser());
    }

    @GetMapping("/all")
    public String getAllUserExpenses(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("expenses", expenseService.findAllByUser(currentUser.getUser()));
        return "expense/userExpenses";
    }

    @GetMapping("/details/{id}")
    public String getExpense(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id, Model model) {
        Optional<Expense> expense = expenseService.findByIdAndUser(id, currentUser.getUser());
        if (expense.isPresent()) {
            model.addAttribute("expense", expense.get());
        } else throw new RuntimeException("Expense not found");
        return "expense/userExpensesDetails";
    }

    //  show expense form
    @GetMapping("/add")
    public String addExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "expense/expenseAddForm";
    }

    @PostMapping("/add")
    public String addExpense(@AuthenticationPrincipal CurrentUser currentUser, @Valid Expense expense,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "expense/expenseAddForm";
        }
        expense.setUser(currentUser.getUser());
        expense.setCreatedOn(LocalDate.now());
        expenseService.saveExpense(expense);

        Long accountId = expense.getAccount().getId();
        Optional<Account> account = accountService.findByIdAndUser(accountId, currentUser.getUser());
        if (account.isPresent()) {
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.subtract(expense.getAmount()));
            accountService.save(account.get());
        } else throw new RuntimeException("Account not found");
        return "redirect:/expenses/all";
    }

    //    show form for editing expense
    @GetMapping("/edit/{id}")
    public String editExpenseForm(Model model, @PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Expense> expense = expenseService.findByIdAndUser(id, currentUser.getUser());
        if (expense.isPresent()) {
            model.addAttribute("expense", expense.get());
        } else throw new RuntimeException("Expense not found");
        return "expense/expenseEditForm";
    }

    //    edit expense
    @PostMapping("/edit/{id}")
    public String editExpense(Expense expense, @AuthenticationPrincipal CurrentUser currentUser,
                              @PathVariable long id) {
        // org expense details

        updatesService.updateAccountWithExpense(expense, currentUser, id);
        return "redirect:/expenses/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Expense> expense = expenseService.findByIdAndUser(id, currentUser.getUser());
        if (expense.isPresent()) {
            expenseService.deleteByIdAndUser(id, currentUser.getUser());
            Long accountId = expense.get().getAccount().getId();
            Optional<Account> account = accountService.findByIdAndUser(accountId, currentUser.getUser());
            if (account.isPresent()) {
                BigDecimal currentBalance = account.get().getBalance();
                account.get().setBalance(currentBalance.add(expense.get().getAmount()));
                accountService.save(account.get());
            } else throw new RuntimeException("Not found");
        } else throw new RuntimeException("Expense not found");
        return "redirect:/expenses/all";
    }

}
