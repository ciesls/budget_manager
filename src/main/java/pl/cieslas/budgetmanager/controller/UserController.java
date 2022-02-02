package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.cieslas.budgetmanager.security.CurrentUser;
import pl.cieslas.budgetmanager.entity.User;
import pl.cieslas.budgetmanager.security.UserService;
import pl.cieslas.budgetmanager.utils.UserUtils.UserUtils;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserUtils userUtils;

    public UserController(UserService userService, UserUtils userUtils) {
        this.userService = userService;
        this.userUtils = userUtils;
    }

    //  show registration form
    @GetMapping("/register")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    //  add new user
    @PostMapping("/register")
    public String createUser(User user) {
        userService.saveUser(user);
        userUtils.addDefaults(user);
        return "registerForm";
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal CurrentUser customUser) {
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.getUsername();
    }

    @GetMapping("/login")
    public String login() {
        return "loginForm";
    }




}
