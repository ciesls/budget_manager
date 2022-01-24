package pl.cieslas.budgetmanager.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.security.UserService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @GetMapping("/save")
    @ResponseBody
    public String saveBudget(@AuthenticationPrincipal CurrentUser customUser) {
        Budget budget = new Budget();
        budget.setName("test4324");
        budget.setAmount(BigDecimal.valueOf(109877));
        //getting current user to retrieve its entities
        budget.setUser(customUser.getUser());
        budgetService.saveBudget(budget);
        return budget.toString();
    }

    @GetMapping("/all")
    @ResponseBody
    public String getAll(@AuthenticationPrincipal CurrentUser customUser) {
        return budgetService.findAllByUser(customUser.getUser()).toString();

    }

    @GetMapping("/delete")
    @ResponseBody
    public String deleteBudget(@AuthenticationPrincipal CurrentUser customUser) {
        budgetService.deleteById(2L);
        return budgetService.findAllByUser(customUser.getUser()).toString();
    }

    @GetMapping("/details/{id}")
    @ResponseBody
    public String getBudgeDetails(@PathVariable long id, @AuthenticationPrincipal CurrentUser customUser) {

        return budgetService.getPerUser(id, customUser.getUser()).toString();
    }

}
