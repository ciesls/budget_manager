package pl.cieslas.budgetmanager.income;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.account.AccountService;
import pl.cieslas.budgetmanager.updates.UpdatesService;
import pl.cieslas.budgetmanager.user.CurrentUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/income")
public class IncomeController {

    private final IncomeService incomeService;
    private final AccountService accountService;
    private final UpdatesService updatesService;

    public IncomeController(IncomeService incomeService, AccountService accountService, UpdatesService updatesService) {
        this.incomeService = incomeService;
        this.accountService = accountService;
        this.updatesService = updatesService;
    }

    @ModelAttribute("accounts")
    public List<Account> accounts(@AuthenticationPrincipal CurrentUser currentUser) {
        return accountService.findAllByUser(currentUser.getUser());
    }

    @ModelAttribute("incomes")
    public List<Income> incomes(@AuthenticationPrincipal CurrentUser currentUser) {
        return incomeService.findAllByUser(currentUser.getUser());
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
        incomeService.updateAccountAfterIncomeAdd(currentUser.getUser(), income);
        return "redirect:/dashboard";
    }

    @GetMapping("/all")
    public String getAllUserIncomes() {
        return "income/userIncomes";
    }

    @GetMapping("/delete/{id}")
    public String deleteIncome(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id) {
        Optional<Income> income = incomeService.findByIdAndUser(id, currentUser.getUser());
        if (income.isPresent()) {
            incomeService.updateAccountAfterIncomeDelete(currentUser.getUser(), income.get());
        } else throw new RuntimeException("Some error");
        incomeService.deleteByIdAndUser(id, currentUser.getUser());

        return "redirect:/income/all";
    }

    @GetMapping("/edit/{id}")
    public String incomeEditForm(Model model, @PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Income> income = incomeService.findByIdAndUser(id, currentUser.getUser());
        if (income.isPresent()) {
            model.addAttribute("income", income.get());
        } else throw new RuntimeException("Income not found");
        return "income/incomeEditForm";
    }

    @PostMapping("/edit/{id}")
    public String editIncome(Income income, @AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id) {
        updatesService.updateAccountWithIncome(income, currentUser.getUser(), id);
        return "redirect:/income/all";
    }


}
