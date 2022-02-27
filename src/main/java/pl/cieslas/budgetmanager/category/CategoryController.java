package pl.cieslas.budgetmanager.category;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.budget.BudgetService;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.updates.Updates;
import pl.cieslas.budgetmanager.user.CurrentUser;
import pl.cieslas.budgetmanager.user.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final BudgetService budgetService;
    private final ExpenseService expenseService;
    private final Updates updates;


    public CategoryController(UserService userService, CategoryService categoryService, BudgetService budgetService,
                              ExpenseService expenseService, Updates updates) {

        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.expenseService = expenseService;
        this.updates = updates;
    }

    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter formatDate() {
        DateTimeFormatter localDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return localDateTimeFormat;
    }

    @ModelAttribute("budgets")
    public List<Budget> budgets(@AuthenticationPrincipal CurrentUser currentUser) {
        return budgetService.findAllByUser(currentUser.getUser());
    }

    //show category form
    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/categoryAddForm";
    }

    //add new category
    @PostMapping("/add")
    public String addCategory(Category category, @AuthenticationPrincipal CurrentUser currentUser) {
        category.setUser(currentUser.getUser());
        categoryService.saveCategory(category);
        return "redirect:/categories/all";
    }

    // show all user's categories
    @GetMapping("/all")
    public String getAllUsersCategories(@AuthenticationPrincipal CurrentUser currentUser, Model model) {
        model.addAttribute("categories", categoryService.findAllByUser(currentUser.getUser()));
        return "categories/userCategories";
    }

    // show expenses in category
    @GetMapping("/catExpenses/{id}")
    public String getAllExpensesFromCategory(@AuthenticationPrincipal CurrentUser currentUser, Model model,
                                             @PathVariable long id) {
        LocalDate monthStart = LocalDate.now().withDayOfMonth(1);
        LocalDate now = LocalDate.now();
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            model.addAttribute("expensesCategories", expenseService.findAllByCategoryAndUser
                    (category.get(), currentUser.getUser()));
            model.addAttribute("categorySum", expenseService.sumOfExpenses
                    (expenseService.findAllByCategoryAndUser(category.get(), currentUser.getUser())));
            model.addAttribute("monthSum", expenseService.sumOfExpenses
                    (expenseService.findAllByCategoryAndUserAndCreatedOnBetween(
                            category.get(), currentUser.getUser(), monthStart, now)));
        } else throw new RuntimeException("Category not found");
        return "expense/userExpensesCategory";
    }

    //    show form for editing category
    @GetMapping("/edit/{id}")
    public String editCategoryForm(Model model, @PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Category> category = categoryService.findByIdAndUser(id, currentUser.getUser());
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
        } else throw new RuntimeException("Category not found");
        return "categories/categoryEditForm";
    }

    //    edit category
    @PostMapping("/edit/{id}")
    public String editCategory(Category category, @AuthenticationPrincipal CurrentUser currentUser) {
        category.setUser(currentUser.getUser());
        categoryService.saveCategory(category);
        return "redirect:/categories/all";
    }

    //    delete category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable long id) {
        Optional<Category> category = categoryService.findByIdAndUser(id, currentUser.getUser());
        if (category.isPresent()) {
            List<Expense> categoryExpenses = expenseService.findAllByCategoryAndUser(category.get(),
                    currentUser.getUser());
            updates.setCategoryOther(category.get(), categoryExpenses, currentUser.getUser());
            categoryService.deleteById(id);
        } else throw new RuntimeException("Category not found");
        return "redirect:/categories/all";
    }


}
