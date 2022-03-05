package pl.cieslas.budgetmanager.account;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.user.CurrentUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ModelAttribute("accounts")
    public List<Account> accounts(@AuthenticationPrincipal CurrentUser currentUser) {
        return accountService.findAllByUser(currentUser.getUser());
    }

    @GetMapping("/add")
    public String addAccountForm(Model model) {
        model.addAttribute("account", new Account());
        return "account/accountAddForm";
    }

    @PostMapping("/add")
    public String addAccount(@AuthenticationPrincipal CurrentUser currentUser, Account account) {

        account.setUser(currentUser.getUser());
        account.setCreatedOn(LocalDate.now());
        accountService.save(account);
        return "redirect:/dashboard";
    }

    @GetMapping("/all")
    public String getAllUserAccounts() {
        return "account/userAccounts";
    }

    @GetMapping("/edit/{id}")
    public String accountEditForm(Model model, @PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Account> account = accountService.findByIdAndUser(id, currentUser.getUser());
        if (account.isPresent()) {
        model.addAttribute("account", account.get());
        } else throw new RuntimeException("Account not found");
        return "account/accountEditForm";
    }

    @PostMapping("/edit/{id}")
    public String editAccount(Account account, @AuthenticationPrincipal CurrentUser currentUser) {
        account.setUser(currentUser.getUser());
        accountService.save(account);
        return "redirect:/account/all";
    }

    @GetMapping("/transfer")
    public String transferForm() {
        return "account/accountTransferForm";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam BigDecimal amount, @RequestParam long account1ID,
                           @RequestParam long account2ID,
                           @AuthenticationPrincipal CurrentUser currentUser) {
        accountService.transfer(amount, account1ID, account2ID, currentUser.getUser());
        return "redirect:/account/all";
    }

}
