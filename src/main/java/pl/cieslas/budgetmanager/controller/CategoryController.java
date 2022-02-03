package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.repository.budget.BudgetService;
import pl.cieslas.budgetmanager.repository.category.CategoryService;
import pl.cieslas.budgetmanager.repository.expense.ExpenseService;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.security.UserService;
import pl.cieslas.budgetmanager.utils.BudgetUtils.BudgetUtils;
import pl.cieslas.budgetmanager.utils.ExpenseUtils.ExpenseUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final UserService userService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final ExpenseService expenseService;
    private final ExpenseUtils expenseUtils;


    public CategoryController(UserService userService, CategoryService categoryService, BudgetService budgetService, ExpenseService expenseService, ExpenseUtils expenseUtils, BudgetUtils budgetUtils) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.expenseService = expenseService;
        this.expenseUtils = expenseUtils;
    }

    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter formatDate() {
        DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return localDateTimeFormat;
    }

    @ModelAttribute("budgets")
    public List<Budget> budgets(@AuthenticationPrincipal CurrentUser customUser) {
        return budgetService.findAllByUser(customUser.getUser());
    }

    //show category form
    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categoryAddForm";
    }

    //add new category
    @PostMapping("/add")
    public String addCategory(Category category, @AuthenticationPrincipal CurrentUser customUser) {
        category.setUser(customUser.getUser());
        categoryService.saveCategory(category);
        return "redirect:/categories/all";
    }

    // show all user's categories
    @GetMapping("/all")
    public String getAllUsersCategories(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("categories", categoryService.findAllByUser(customUser.getUser()));
        return "userCategories";
    }

    // show expenses in category
    @GetMapping("/catExpenses/{id}")
    public String getAllExpensesFromCategory(@AuthenticationPrincipal CurrentUser customUser, Model model, @PathVariable long id) {
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        Optional<Category> category = categoryService.findById(id);
        List<Expense> expensesCategories = expenseService.findAllByCategoryAndUser(category.get(), customUser.getUser());

        model.addAttribute("expensesCategories", expenseService.findAllByCategoryAndUser(category.get(), customUser.getUser()));
        model.addAttribute("categorySum", expenseUtils.sumOfExpenses(expenseService.findAllByCategoryAndUser(
                category.get(), customUser.getUser())));
        model.addAttribute("monthSum", expenseUtils.sumOfExpenses(expenseService.findAllByCategoryAndUserAndCreatedOnBetween(
                category.get(), customUser.getUser(), monthStart, now)));

        return "userExpensesCategory";
    }

    //    show form for editing category
    @GetMapping("/edit/{id}")
    public String editCategoryForm(Model model, @PathVariable long id) {
        model.addAttribute("category", categoryService.findById(id));
        return "categoryEditForm";
    }

    //    edit category
    @PostMapping("/edit/{id}")
    public String editCategory(Category category, @AuthenticationPrincipal CurrentUser customUser) {
        category.setUser(customUser.getUser());
        categoryService.saveCategory(category);
        return "redirect:/categories/all";
    }

}
