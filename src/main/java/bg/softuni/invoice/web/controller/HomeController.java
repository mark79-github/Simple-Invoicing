package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.service.UserService;
import bg.softuni.invoice.web.annotation.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @PageTitle("Welcome")
    public String index(Principal principal,
                        Model model) {

        if (principal != null) {
            model.addAttribute("user", this.userService.loadUserByUsername(principal.getName()));
            return "home/home";
        }

        return "home/index";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public String home(Principal principal,
                       Model model) {

        model.addAttribute("user", this.userService.loadUserByUsername(principal.getName()));
        return "home/home";
    }
}
