package pl.cieslas.budgetmanager.budget;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.dto.BudgetDetailsDTOService;
import pl.cieslas.budgetmanager.updates.UpdatesService;
import pl.cieslas.budgetmanager.user.CurrentUser;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final UpdatesService updatesService;
    private final BudgetDetailsDTOService budgetDetailsDTOService;

    public BudgetController(BudgetService budgetService, CategoryService categoryService, UpdatesService updatesService, BudgetDetailsDTOService budgetDetailsDTOService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.updatesService = updatesService;
        this.budgetDetailsDTOService = budgetDetailsDTOService;
    }

    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter formatDate() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    //  show budget form
    @GetMapping("/add")
    public String addBudgetForm(Model model) {
        model.addAttribute("budget", new Budget());
        return "budget/budgetAddForm";
    }

    //  add new budget
    @PostMapping("/add")
    public String addBudget(@Valid Budget budget, BindingResult result,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        if (result.hasErrors()) {
            return "budget/budgetAddForm";
        }
        budget.setUser(currentUser.getUser());
        budgetService.saveBudget(budget);
        return "redirect:/budgets/all";
    }

    //  get all users details
    @GetMapping("/all")
    public String getUserBudgets(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("budgets", budgetService.findAllByUser(currentUser.getUser()));
        return "budget/userBudgets";
    }

    //    show form for editing budget
    @GetMapping("/edit/{id}")
    public String editBudgetForm(Model model, @PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Budget> budget = budgetService.findByIdAndUser(id, currentUser.getUser());
        if (budget.isPresent()) {
            model.addAttribute("budget", budget.get());
        } else throw new RuntimeException("Budget not found");
        return "budget/budgetEditForm";
    }

    //    edit budget
    @PostMapping("/edit/{id}")
    public String editBudget(Budget budget, @AuthenticationPrincipal CurrentUser currentUser) {
        budget.setUser(currentUser.getUser());
        budgetService.saveBudget(budget);
        return "redirect:/budgets/all";
    }

    @GetMapping("/details/{id}")
    public String getBudgeDetails(@PathVariable long id,
                                  @AuthenticationPrincipal CurrentUser currentUser, Model model) {
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        model.addAttribute("budgetDetails", budgetDetailsDTOService.getBudgetDetailsDTO(
                currentUser.getUser(),monthStart, now, id));
        return "budget/userBudgetsDetails";
    }

    //    show categories in budget
    @GetMapping("/budgetCategories/{id}")
    public String getAllCategoriesFromBudget(@AuthenticationPrincipal CurrentUser currentUser,
                                             @PathVariable long id, Model model) {
        Optional<Budget> budget = budgetService.findByIdAndUser(id, currentUser.getUser());
        if (budget.isPresent()) {
            List<Category> budgetCategories = categoryService.findAllByUserAndBudget(currentUser.getUser(), budget.get());
            model.addAttribute("categoriesBudget", budgetCategories);
        } else throw new RuntimeException("Budget not found");
        return "categories/userCategoriesBudgets";
    }

    @GetMapping("/budgetExpenses/{id}")
    public String getAllExpensesFromBudget(@AuthenticationPrincipal CurrentUser currentUser, Model model,
                                           @PathVariable long id) {
        Optional<Budget> budget = budgetService.findByIdAndUser(id, currentUser.getUser());
        if (budget.isPresent()) {
            List<Category> budgetCategories = categoryService.findAllByUserAndBudget(currentUser.getUser(), budget.get());
            model.addAttribute("budgetExpenses",
                    budgetService.getBudgetExpenses(budgetCategories, currentUser.getUser()));
        } else throw new RuntimeException("Budget not found");
        return "budget/userBudgetExpenses";
    }

    //    delete budget and set its categories do Other
    @GetMapping("/delete/{id}")
    public String deleteBudget(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id) {
        Optional<Budget> budget = budgetService.findByIdAndUser(id, currentUser.getUser());
        if (budget.isPresent()) {
            List<Category> categoriesBudget = categoryService.findAllByUserAndBudget(currentUser.getUser(), budget.get());
            updatesService.setBudgetOther(budget.get(), categoriesBudget, currentUser.getUser());
            budgetService.deleteById(id);
        } else throw new RuntimeException("Budget not found");
        return "redirect:/budgets/all";
    }
}
