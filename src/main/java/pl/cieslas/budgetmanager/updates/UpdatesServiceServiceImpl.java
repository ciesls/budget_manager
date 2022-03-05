package pl.cieslas.budgetmanager.updates;

import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.account.AccountService;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.budget.BudgetService;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.income.Income;
import pl.cieslas.budgetmanager.income.IncomeService;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UpdatesServiceServiceImpl implements UpdatesService {

    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final AccountService accountService;

    public UpdatesServiceServiceImpl(BudgetService budgetService, CategoryService categoryService, ExpenseService expenseService, IncomeService incomeService, AccountService accountService) {
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.accountService = accountService;
    }

    @Override
    public void setBudgetOther(Budget budget, List<Category> categories, User user) {
        checkIfBudgetAndCategoryOtherExists(user);
        categories = categoryService.findAllByUserAndBudget(user, budget);
        for (Category category : categories) {
            category.setBudget(budgetService.findByNameAndUser("Other", user));
        }
    }

    @Override
    public void setCategoryOther(Category category, List<Expense> expenses, User user) {
        checkIfBudgetAndCategoryOtherExists(user);
        expenses = expenseService.findAllByCategoryAndUser(category, user);
        for (Expense expense : expenses) {
            expense.setCategory(categoryService.findByNameAndUser("Other", user));

        }
    }

    @Override
    public void updateAccountWithIncome(Income income, User user, long id) {
        Optional<Income> orgIncome = incomeService.findByIdAndUser(id, user);
        if (orgIncome.isPresent()) {
            Account orgAccount = orgIncome.get().getAccount();
            BigDecimal orgAmount = orgIncome.get().getAmount();
            BigDecimal orgAccBalance = orgAccount.getBalance();

            income.setUser(user);
            incomeService.save(income);

            Optional<Income> updatedIncome = incomeService.findByIdAndUser(id, user);
//      details of updated account
            if (updatedIncome.isPresent()) {
                BigDecimal updatedAmount = updatedIncome.get().getAmount();
                Account updatedAccount = updatedIncome.get().getAccount();
                BigDecimal updatedAccBalance = updatedAccount.getBalance();

                accountService.updateAccountWithAmountIncome(orgAccount, orgAmount, orgAccBalance, updatedAmount,
                        updatedAccount, updatedAccBalance);
            } else throw new RuntimeException("Not found");
        } else throw new RuntimeException("Not found");
    }

    @Override
    public void updateAccountWithExpense(Expense expense, User user, long id) {
        Optional<Expense> orgExpense = expenseService.findByIdAndUser(id, user);
        if (orgExpense.isPresent()) {
            Account orgAccount = orgExpense.get().getAccount();
            BigDecimal orgAmount = orgExpense.get().getAmount();
            BigDecimal orgAccBalance = orgAccount.getBalance();

            expense.setUser(user);
            expenseService.saveExpense(expense);

            Optional<Expense> updatedExpense = expenseService.findByIdAndUser(id, user);
            //        updated expense details
            if (updatedExpense.isPresent()) {
                BigDecimal updatedAmount = updatedExpense.get().getAmount();
                Account updatedAccount = updatedExpense.get().getAccount();
                BigDecimal updatedAccBalance = updatedAccount.getBalance();

                accountService.updateAccountWithAmountExpense(orgAccount, orgAmount, orgAccBalance, updatedAmount,
                        updatedAccount, updatedAccBalance);
            } else throw new RuntimeException("Not found");
        } else throw new RuntimeException("Not found");
    }

    @Override
    public void checkIfBudgetAndCategoryOtherExists(User user) {

        Budget budgetOther = budgetService.findByNameAndUser("Other", user);
        if (budgetOther == null) {
            Budget newBudget = new Budget();
            newBudget.setName("Other");
            newBudget.setAmount(BigDecimal.ZERO);
            newBudget.setUser(user);
            budgetService.saveBudget(newBudget);
        }
        Category categoryOther = categoryService.findByNameAndUser("Other", user);
        if (categoryOther == null) {
            Category newCategory = new Category();
            newCategory.setUser(user);
            newCategory.setName("Other");
            newCategory.setBudget(budgetService.findByNameAndUser("Other", user));
            categoryService.saveCategory(newCategory);

        }

    }

}

