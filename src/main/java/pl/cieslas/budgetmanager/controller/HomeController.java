package pl.cieslas.budgetmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "landingPage";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "landingPage";
    }


}