package pl.cieslas.budgetmanager.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);

    Optional<Category> findByIdAndUser(Long id, User user);

    Category save(Category category);

    void deleteById(Long id);

    List<Category> findAllByUser(User user);


}
