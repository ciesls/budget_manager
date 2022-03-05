package pl.cieslas.budgetmanager.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.cieslas.budgetmanager.account.Account;
import pl.cieslas.budgetmanager.account.AccountService;
import pl.cieslas.budgetmanager.budget.Budget;
import pl.cieslas.budgetmanager.budget.BudgetService;
import pl.cieslas.budgetmanager.category.Category;
import pl.cieslas.budgetmanager.category.CategoryService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BudgetService budgetService;
    private final CategoryService categoryService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountService accountService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BudgetService budgetService, CategoryService categoryService, BCryptPasswordEncoder passwordEncoder, AccountService accountService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.budgetService = budgetService;
        this.categoryService = categoryService;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);

    }

    public void addDefaults(User user) {
        Category category = new Category();
        Budget budget = new Budget();
        Account account = new Account();
        budget.setUser(user);
        budget.setName("Other");
        category.setUser(user);
        category.setName("Other");
        budget.setAmount(BigDecimal.valueOf(0.00));
        budgetService.saveBudget(budget);
        category.setBudget(budget);
        categoryService.saveCategory(category);
        account.setUser(user);
        account.setName("Default");
        account.setCreatedOn(LocalDate.now());
        account.setBalance(BigDecimal.valueOf(0.00));
        accountService.save(account);
    }
}
