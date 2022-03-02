package pl.cieslas.budgetmanager.category;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.expense.ExpenseService;
import pl.cieslas.budgetmanager.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Primary
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ExpenseService expenseService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ExpenseService expenseService) {
        this.categoryRepository = categoryRepository;
        this.expenseService = expenseService;
    }

    @Override
    public Optional<Category> findByIdAndUser(Long id, User user) {
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
    public Category findByNameAndUser(String name, User user) {
        return categoryRepository.findByNameAndUser(name, user);
    }

    @Override
    public List<Category> findAllByUserAndBudget(User user, Budget budget) {
        return categoryRepository.findAllByUserAndBudget(user, budget);
    }

    @Override
    public Map<Category, BigDecimal> getCategorySum(User user, List<Category> categories, LocalDate startTime, LocalDate endTime) {

        Map<Category, BigDecimal> categorySumMap = new HashMap<>();

        categories = categoryRepository.findAllByUser(user);
        for (Category category : categories) {
            BigDecimal categorySum = expenseService.sumOfExpenses(expenseService.
                    findAllByCategoryAndUserAndCreatedOnBetween(category, user, startTime, endTime));
            categorySumMap.put(category, categorySum);
        }

        return categorySumMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }


}


