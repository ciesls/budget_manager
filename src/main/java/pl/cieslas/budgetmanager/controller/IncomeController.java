package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.entity.Account;
import pl.cieslas.budgetmanager.entity.Income;
import pl.cieslas.budgetmanager.repository.account.AccountService;
import pl.cieslas.budgetmanager.repository.income.IncomeService;
import pl.cieslas.budgetmanager.security.CurrentUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;
    private final AccountService accountService;


    @ModelAttribute("accounts")
    public List<Account> accounts(@AuthenticationPrincipal CurrentUser currentUser) {
        return accountService.findAllByUser(currentUser.getUser());
    }

    @ModelAttribute("incomes")
    public List<Income> incomes(@AuthenticationPrincipal CurrentUser currentUser) {
        return incomeService.findAllByUser(currentUser.getUser());
    }

    public IncomeController(IncomeService incomeService, AccountService accountService) {
        this.incomeService = incomeService;
        this.accountService = accountService;
    }

    @GetMapping("/add")
    public String addIncomeForm(Model model) {
        model.addAttribute("income", new Income());
        return "income/incomeAddForm";
    }

    @PostMapping("add")
    public String addIncome(@AuthenticationPrincipal CurrentUser currentUser, Income income) {
        income.setUser(currentUser.getUser());
        income.setCreatedOn(LocalDate.now());
        incomeService.save(income);
        Long accountId = income.getAccount().getId();
        Optional<Account> account = accountService.findById(accountId);
        if (account.isPresent()){
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.add(income.getAmount()));
            accountService.save(account.get());
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/all")
    public String getAllUserIncomes() {
        return "income/userIncomes";
    }

    @GetMapping("/delete/{id}")
    public String deleteIncome(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id) {
        Optional<Income> income = incomeService.findByIdAndUser(id, currentUser.getUser());
        Long accountId = income.get().getAccount().getId();
        Optional<Account> account = accountService.findById(accountId);
        if (account.isPresent()){
            BigDecimal currentBalance = account.get().getBalance();
            account.get().setBalance(currentBalance.subtract(income.get().getAmount()));
            accountService.save(account.get());
        }
        incomeService.deleteByIdAndUser(id, currentUser.getUser());

        return "redirect:/income/all";
    }



}
