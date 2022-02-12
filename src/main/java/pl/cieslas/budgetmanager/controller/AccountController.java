package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.entity.Account;
import pl.cieslas.budgetmanager.repository.account.AccountService;
import pl.cieslas.budgetmanager.security.CurrentUser;

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
    public String getAlluserAccounts() {
        return "account/userAccounts";
    }

    @GetMapping("/edit/{id}")
    public String accountEditForm(Model model, @PathVariable long id) {
        model.addAttribute("account", accountService.findById(id));
        return "account/accountEditForm";
    }

    @PostMapping("/edit/{id}")
    public String editAccount(Account account, @AuthenticationPrincipal CurrentUser currentUser) {
        account.setUser(currentUser.getUser());
        accountService.save(account);
        return "redirect:/account/all";
    }

    @GetMapping("/transfer")
    public String transferForm(Model model) {
        return "account/accountTransferForm";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam BigDecimal amount, @RequestParam long account1ID,
                           @RequestParam long account2ID,
                           @AuthenticationPrincipal CurrentUser currentUser) {

        System.out.println(amount);
        System.out.println(account1ID);
        System.out.println(account2ID);
        Optional<Account> acc1 = accountService.findByIdAndUser(account1ID, currentUser.getUser());
        Optional<Account> acc2 = accountService.findByIdAndUser(account2ID, currentUser.getUser());
        BigDecimal acc1Balance = acc1.get().getBalance();
        BigDecimal acc2Balance = acc2.get().getBalance();
        acc2.get().setBalance(acc2Balance.add(amount));
        acc1.get().setBalance(acc1Balance.subtract(amount));
        accountService.save(acc1.get());
        accountService.save(acc2.get());

        return "redirect:/account/all";
    }

}
