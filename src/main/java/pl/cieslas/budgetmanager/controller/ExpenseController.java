package pl.cieslas.budgetmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.security.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserService userService;

    @RequestMapping("/all")
    @ResponseBody
    public String getAllUserExpenses(@AuthenticationPrincipal CurrentUser customUser) {
        return expenseService.findAllByUser(customUser.getUser()).toString();
    }

    @GetMapping("/details/{id}")
    @ResponseBody
    public String getExpense(@AuthenticationPrincipal CurrentUser customUser, @PathVariable long id ) {
        return expenseService.getPerUser(id, customUser.getUser()).toString();
    }

    @GetMapping("/save")
    @ResponseBody
    public String saveExpense(@AuthenticationPrincipal CurrentUser customUser) {
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(650));
        expense.setName("testcontroller");
        expense.setDescription("gbweuigbeui");
        expense.setUser(customUser.getUser());
        expense.setCreated_on(LocalDateTime.now());
        expenseService.saveExpense(expense);
        return expense.toString();
    }


}
