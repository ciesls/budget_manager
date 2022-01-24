package pl.cieslas.budgetmanager.repository.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    @Override
    public Optional<Expense> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Expense> getPerUser(Long id, User user) {
        return expenseRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Expense> findAllByUser(User user) {
        return expenseRepository.findAllByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        expenseRepository.deleteById(id);

    }

    @Override
    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    @Override
    public void add(Expense expense) {

    }

    @Override
    public void update(Expense expense) {

    }
}
