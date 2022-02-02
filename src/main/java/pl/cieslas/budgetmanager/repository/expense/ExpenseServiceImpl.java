package pl.cieslas.budgetmanager.repository.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.Expense;
import pl.cieslas.budgetmanager.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional
public class ExpenseServiceImpl implements ExpenseService{

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    @Override
    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
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
    public void deleteByIdAndUser(Long id, User user) {
        expenseRepository.deleteByIdAndUser(id, user);
    }

    @Override
    public void saveExpense(Expense expense) {
        expenseRepository.save(expense);
    }

    @Override
    public void update(Expense expense) {

    }

    @Override
    public List<Expense> findAllByCategoryAndUser(Category category, User user) {
        return expenseRepository.findAllByCategoryAndUser(category, user);
    }

    @Override
    public List<Expense> findAllByUserAndCreatedOnBetween(User user, LocalDate monthStart, LocalDate currentTime) {
        return expenseRepository.findAllByUserAndCreatedOnBetween(user, monthStart, currentTime);
    }

    @Override
    public List<Expense> findTop5ByUserOrderByIdDesc(User user) {
        return expenseRepository.findTop5ByUserOrderByIdDesc(user);
    }

    @Override
    public List<Expense> findAllByCategoryAndUserAndCreatedOnBetween(Category category, User user, LocalDate monthStart, LocalDate currentTime) {
        return expenseRepository.findAllByCategoryAndUserAndCreatedOnBetween(category, user, monthStart, currentTime);
    }


}
