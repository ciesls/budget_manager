package pl.cieslas.budgetmanager.updates;

import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.expense.Expense;
import pl.cieslas.budgetmanager.income.Income;
import pl.cieslas.budgetmanager.user.User;

import java.util.List;

public interface UpdatesService {

    void setBudgetOther(Budget budget, List<Category> categories, User user);

    void setCategoryOther(Category category, List<Expense> expenses, User user);

    void updateAccountWithIncome(Income income, User user, long id);

    void updateAccountWithExpense(Expense expense, User user, long id);

    void checkIfBudgetAndCategoryOtherExists(User user);

}
