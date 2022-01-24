package pl.cieslas.budgetmanager.repository.category;

import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> get(Long id);

    Optional<Category> getCategoryPerUser(Long id, User user);

    List<Category> findAllByUser(User user);

    void deleteById(Long id);

    void saveCategory(Category category);

    void update(Category category);


}
