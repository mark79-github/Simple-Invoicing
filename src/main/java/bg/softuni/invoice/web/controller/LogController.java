package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.service.LogService;
import bg.softuni.invoice.web.annotation.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/all")
    @PageTitle("Log all")
    @PreAuthorize("hasRole('ROOT')")
    public String all() {

        return "log/all";
    }
}
