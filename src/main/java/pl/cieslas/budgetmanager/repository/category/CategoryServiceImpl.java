package pl.cieslas.budgetmanager.repository.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

@Service
@Primary
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Optional<Category> getCategoryPerUser(Long id, User user) {
        return categoryRepository.findByIdAndUser(id, user);
    }

    @Override
    public List<Category> findAllByUser(User user) {
        return categoryRepository.findAllByUser(user);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);

    }

    @Override
    public void update(Category category) {
        categoryRepository.save(category);

    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findAllByUserAndBudget(User user, Optional<Budget> budget) {
        return categoryRepository.findAllByUserAndBudget(user, budget);
    }

}
