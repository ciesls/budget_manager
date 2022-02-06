package pl.cieslas.budgetmanager.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cieslas.budgetmanager.entity.Budget;
import pl.cieslas.budgetmanager.entity.Category;
import pl.cieslas.budgetmanager.entity.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);

    Optional<Category> findByIdAndUser(Long id, User user);

    List<Category> findAllByUser(User user);

    void deleteById(Long id);

    Category save(Category category);

    Category findByNameAndUser(String name, User user);

    List<Category> findAllByUserAndBudget(User user, Budget budget);


}
