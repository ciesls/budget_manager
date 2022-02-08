package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.cieslas.budgetmanager.entity.Account;
import pl.cieslas.budgetmanager.repository.account.AccountRepository;
import pl.cieslas.budgetmanager.repository.account.AccountService;
import pl.cieslas.budgetmanager.security.CurrentUser;

import java.time.LocalDate;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/add")
    public String addAccountForm(Model model) {
        model.addAttribute("account", new Account());
        return "accountAddForm";
    }

    @PostMapping("/add")
    public String addAccount(@AuthenticationPrincipal CurrentUser currentUser, Account account) {

        account.setUser(currentUser.getUser());
        account.setCreatedOn(LocalDate.now());
        accountService.save(account);

        return "redirect:/dashboard";
    }


}
