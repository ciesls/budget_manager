package pl.cieslas.budgetmanager.category;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.budget.BudgetService;
import pl.cieslas.budgetmanager.dto.CategoryExpensesDTOService;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.updates.UpdatesService;
import pl.cieslas.budgetmanager.user.CurrentUser;

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
    private final UpdatesService updatesService;
    private final CategoryExpensesDTOService categoryExpensesDTOService;


    public CategoryController(CategoryService categoryService, BudgetService budgetService,
                              ExpenseService expenseService, UpdatesService updatesService,
                              CategoryExpensesDTOService categoryExpensesDTOService) {

        this.categoryService = categoryService;
        this.budgetService = budgetService;
        this.expenseService = expenseService;
        this.updatesService = updatesService;
        this.categoryExpensesDTOService = categoryExpensesDTOService;
    }

    @ModelAttribute("localDateTimeFormat")
    public DateTimeFormatter formatDate() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
        model.addAttribute("categoryExpenses", categoryExpensesDTOService.getCategoryExpensesDTO(
                currentUser.getUser(), monthStart, now, id));
        return "expense/userExpensesCategory";
    }
//    }

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
            updatesService.setCategoryOther(category.get(), categoryExpenses, currentUser.getUser());
            categoryService.deleteById(id);
        } else throw new RuntimeException("Category not found");
        return "redirect:/categories/all";
    }


}
