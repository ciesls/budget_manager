package pl.cieslas.budgetmanager.repository.category;

import org.springframework.beans.factory.annotation.Autowired;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.User;
import pl.cieslas.budgetmanager.repository.expense.ExpenseRepository;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Optional<Category> get(Long id) {
        return categoryRepository.findById(id);
    }

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
}
