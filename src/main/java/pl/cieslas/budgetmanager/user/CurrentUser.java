package pl.cieslas.budgetmanager.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {

    private final pl.cieslas.budgetmanager.user.User user;

    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.cieslas.budgetmanager.user.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public pl.cieslas.budgetmanager.user.User getUser() {
        return user;
    }
}