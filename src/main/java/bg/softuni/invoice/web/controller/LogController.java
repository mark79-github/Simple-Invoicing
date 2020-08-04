package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.view.LogViewModel;
import bg.softuni.invoice.service.LogService;
import bg.softuni.invoice.web.annotation.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.GlobalConstants.ANONYMOUS_USER_USERNAME;

@Controller
@RequestMapping("/log")
public class LogController {

    private final LogService logService;
    private final ModelMapper modelMapper;

    @Autowired
    public LogController(LogService logService, ModelMapper modelMapper) {
        this.logService = logService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PageTitle("Log all")
    @PreAuthorize("hasRole('ADMIN')")
    public String all(Model model) {

        if (!model.containsAttribute("logs")) {
            List<LogViewModel> logs = this.logService.getAllLogs();
            model.addAttribute("logs", logs);
            model.addAttribute("comparator", Comparator.comparing(LogViewModel::getDateTime));
        }

        return "log/all";
    }
}
