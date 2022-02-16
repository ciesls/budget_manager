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

    public ExpenseController(ExpenseService expenseService, UserService userService,
                             CategoryService categoryService, AccountService accountService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @ModelAttribute("categories")
    public List<Category> categories(@AuthenticationPrincipal CurrentUser currentUser) {
        return categoryService.findAllByUser(currentUser.getUser());
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
    public String getAllUserExpenses(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("expenses", expenseService.findAllByUser(currentUser.getUser()));
        return "expense/userExpenses";
    }

    @GetMapping("/details/{id}")
    public String getExpense(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id, Model model) {
        Optional<Expense> expense = expenseService.getPerUser(id, currentUser.getUser());
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
    public String addExpense(@AuthenticationPrincipal CurrentUser currentUser, @Valid Expense expense,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "expense/expenseAddForm";
        }
        expense.setUser(currentUser.getUser());
        expense.setCreatedOn(LocalDate.now());
        expenseService.saveExpense(expense);

        Long accountId = expense.getAccount().getId();
        Optional<Account> account = accountService.findById(accountId);
        if (account.isPresent()) {
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
    public String editExpense(Expense expense, @AuthenticationPrincipal CurrentUser currentUser,
                              @PathVariable long id) {
        // org expense details
        Optional<Expense> orgExpense = expenseService.findById(id);
        Account orgAccount = orgExpense.get().getAccount();
        BigDecimal orgAmount = orgExpense.get().getAmount();
        BigDecimal orgAccBalance = orgAccount.getBalance();

        expense.setUser(currentUser.getUser());
        expenseService.saveExpense(expense);

        //        updated expense details
        Optional<Expense> updatedExpense = expenseService.findById(id);
        BigDecimal updatedAmount = updatedExpense.get().getAmount();
        Account updatedAccount = updatedExpense.get().getAccount();
        BigDecimal updatedAccBalance = updatedAccount.getBalance();

        int result = orgAmount.compareTo(updatedAmount);

        if ((orgAccount == updatedAccount) && (result == -1)) { //if same acc && updated amt > org amt; updating only orgAccount
            BigDecimal delta = updatedAmount.subtract(orgAmount);
            orgAccount.setBalance(orgAccBalance.subtract(delta));
            accountService.save(orgAccount);

        } else if ((orgAccount == updatedAccount) && (result == 1)) { //if same acc && org amt > updated amt; updating only orgAccount
            BigDecimal delta = orgAmount.subtract(updatedAmount);
            orgAccount.setBalance(orgAccBalance.add(delta));
            accountService.save(orgAccount);

        } else if (orgAccount != updatedAccount) { //account change
            // subtract from orgAccount full amount, add to new account full amount
            updatedAccount.setBalance(updatedAccBalance.subtract(updatedAmount));
            accountService.save(updatedAccount);
            orgAccount.setBalance(orgAccBalance.add(orgAmount));
            accountService.save(orgAccount);
        }
        return "redirect:/expenses/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Expense> expense = expenseService.findById(id);
        expenseService.deleteByIdAndUser(id, currentUser.getUser());

        Long accountId = expense.get().getAccount().getId();
        Optional<Account> account = accountService.findById(accountId);
        if (account.isPresent()) {
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.add(expense.get().getAmount()));
            accountService.save(account.get());
        }

        return "redirect:/expenses/all";
    }


}
