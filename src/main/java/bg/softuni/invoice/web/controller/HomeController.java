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

    @GetMapping("/")
    @PageTitle("invoices")
    public String index(Principal principal) {

        if (principal != null) {
            return "home/home";
        }

        return "home/index";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public String home() {

        return "home/home";
    }
}
