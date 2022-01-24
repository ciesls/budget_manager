package pl.cieslas.budgetmanager.security;

import pl.cieslas.budgetmanager.entity.User;

public interface UserService {
    User findByUserName(String name);

    void saveUser(User user);
}
