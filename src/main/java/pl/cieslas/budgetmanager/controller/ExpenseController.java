package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.security.UserService;

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

    public ExpenseController(ExpenseService expenseService, UserService userService, CategoryService categoryService) {
        this.expenseService = expenseService;
        this.userService = userService;
        this.categoryService = categoryService;
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


    @RequestMapping("/all")
    public String getAllUserExpenses(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("expenses", expenseService.findAllByUser(customUser.getUser()));
        return "userExpenses";
    }

    @GetMapping("/details/{id}")
    public String getExpense(@AuthenticationPrincipal CurrentUser customUser, @PathVariable long id, Model model) {
        Optional<Expense> expense = expenseService.getPerUser(id, customUser.getUser());
        if (expense.isPresent()){
            model.addAttribute("expense", expense.get());
        }
        return "userExpensesDetails";
    }

    //  show expense form
    @GetMapping("/add")
    public String addExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "expenseAddForm";
    }

    @PostMapping("/add")
    public String addExpense(@AuthenticationPrincipal CurrentUser customUser, Expense expense) {
        expense.setUser(customUser.getUser());
        expense.setCreatedOn(LocalDate.now());
        expenseService.saveExpense(expense);
        return "redirect:/expenses/all";
    }

    //    show form for editing expense
    @GetMapping("/edit/{id}")
    public String editExpenseForm(Model model, @PathVariable long id) {
        model.addAttribute("expense", expenseService.findById(id));
        return "expenseEditForm";
    }

    //    edit expense
    @PostMapping("/edit/{id}")
    public String editExpense(Expense expense, @AuthenticationPrincipal CurrentUser customUser) {
        expense.setUser(customUser.getUser());
        expenseService.saveExpense(expense);
        return "redirect:/expenses/all";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteExpense(@PathVariable long id, @AuthenticationPrincipal CurrentUser customUser) {
        expenseService.deleteByIdAndUser(id, customUser.getUser());
        return "redirect:/expenses/all";
    }


}
