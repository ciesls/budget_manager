package pl.cieslas.budgetmanager.savings;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.cieslas.budgetmanager.user.CurrentUser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/savings")
public class SavingsController {

    private final SavingsService savingsService;

    public SavingsController(SavingsService savingsService) {
        this.savingsService = savingsService;
    }

    @ModelAttribute("allSavings")
    public List<Savings> allSavings(@AuthenticationPrincipal CurrentUser currentUser) {
        return savingsService.findAllByUser(currentUser.getUser());
    }

    @GetMapping("/add")
    public String addSavingForm(Model model) {
        model.addAttribute("savings", new Savings());
        return "savings/savingsAddForm";
    }

    @PostMapping("/add")
    public String addSavings(@AuthenticationPrincipal CurrentUser currentUser, Savings savings) {
        savings.setUser(currentUser.getUser());
        savingsService.save(savings);
        return "redirect:/savings/all";
    }

    @GetMapping("/all")
    public String allUserSavings() {
        return "savings/userSavings";
    }

    @GetMapping("/delete/{id}")
    public String deleteSaving(@PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        savingsService.deleteByIdAndUser(id, currentUser.getUser());
        return "redirect:/savings/all";
    }

    @GetMapping("/edit/{id}")
    public String editSavingForm(Model model, @PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Savings> saving = savingsService.findByIdAndUser(id, currentUser.getUser());
        if (saving.isPresent()) {
            model.addAttribute("savings", saving.get());
        } else throw new RuntimeException("Saving not found");
        return "savings/savingsEditForm";
    }

    @PostMapping("/edit/{id}")
    public String editSaving(Savings savings, @AuthenticationPrincipal CurrentUser currentUser) {
        savings.setUser(currentUser.getUser());
        savingsService.save(savings);
        return "redirect:/savings/all";
    }

    @GetMapping("/increase/{id}")
    public String increaseSavingFrom(Model model, @PathVariable long id, @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Savings> saving = savingsService.findByIdAndUser(id, currentUser.getUser());
        if (saving.isPresent()) {
            model.addAttribute("savings", saving.get());
        } else throw new RuntimeException("Saving not found");
        return "savings/savingsIncreaseForm";
    }

    @PostMapping("/increase/{id}")
    public String increaseValue(@RequestParam BigDecimal newValue, @PathVariable long id,
                                @AuthenticationPrincipal CurrentUser currentUser) {

        Optional<Savings> saving = savingsService.findByIdAndUser(id, currentUser.getUser());
        if (saving.isPresent()) {
            saving.get().setPreviousValue(saving.get().getValue());
            saving.get().setValue(newValue);
            savingsService.save(saving.get());
        } else throw new RuntimeException("Savings not found");

        return "redirect:/savings/all";
    }
}
