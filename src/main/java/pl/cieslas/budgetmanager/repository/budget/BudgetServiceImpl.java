package pl.cieslas.budgetmanager.repository.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Optional<Budget> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Budget> getPerUser(Long id, User user) {
        return budgetRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Budget> findAllByUser(User user) {
        return budgetRepository.findAllByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        budgetRepository.deleteById(id);
    }

    @Override
    public void saveBudget(Budget budget) {
        budgetRepository.save(budget);
    }

    @Override
    public void update(Budget budget) {
        budgetRepository.save(budget);
    }

}
