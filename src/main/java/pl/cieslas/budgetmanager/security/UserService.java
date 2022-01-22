package pl.cieslas.budgetmanager.security;

public interface UserService {
    User findByUserName(String name);

    void saveUser(User user);
}
