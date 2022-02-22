package pl.cieslas.budgetmanager.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cieslas.budgetmanager.entity.Savings;
import pl.cieslas.budgetmanager.repository.savings.SavingsService;
import pl.cieslas.budgetmanager.security.CurrentUser;

@Controller
@RequestMapping("/savings")
public class SavingsController {

    private final SavingsService savingsService;

    public SavingsController(SavingsService savingsService) {
        this.savingsService = savingsService;
    }

    @GetMapping("/add")
    public String addSavingForm(Model model){
        model.addAttribute("savings", new Savings());
        return "savings/savingsAddForm";

    }

    @PostMapping("/add")
    public String addSavings(@AuthenticationPrincipal CurrentUser currentUser, Savings savings) {
        savings.setUser(currentUser.getUser());
        savingsService.save(savings);
        return "redirect:/dashboard";
    }


}
