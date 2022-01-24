package pl.cieslas.budgetmanager.security;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.cieslas.budgetmanager.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
