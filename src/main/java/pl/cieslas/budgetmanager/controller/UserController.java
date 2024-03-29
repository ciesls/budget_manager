package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.cieslas.budgetmanager.user.CurrentUser;
import pl.cieslas.budgetmanager.user.User;
import pl.cieslas.budgetmanager.user.UserService;

@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //  show registration form
    @GetMapping("/register")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user/registerForm";
    }

    //  add new user
    @PostMapping("/register")
    public String createUser(User user) {
        userService.saveUser(user);
        userService.addDefaults(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(@AuthenticationPrincipal CurrentUser currentUser) {
        User entityUser = currentUser.getUser();
        return "Hello " + entityUser.getUsername();
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/dashboard";
    }




}
