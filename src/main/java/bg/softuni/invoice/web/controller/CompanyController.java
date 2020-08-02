package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.bind.CompanyAddBindingModel;
import bg.softuni.invoice.model.bind.CompanyEditBindingModel;
import bg.softuni.invoice.model.service.CompanyServiceModel;
import bg.softuni.invoice.model.view.CompanyViewModel;
import bg.softuni.invoice.service.CompanyService;
import bg.softuni.invoice.web.annotation.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyController(CompanyService companyService, ModelMapper modelMapper) {
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PageTitle("Company add")
    @PreAuthorize("hasRole('ADMIN')")
    public String add(Model model) {

        if (!model.containsAttribute("companyAddBindingModel")) {
            model.addAttribute("companyAddBindingModel", new CompanyAddBindingModel());
        }

        return "company/add";
    }

    @PostMapping("/add")
//    @PageTitle("Post mapping - add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addConfirm(@Valid
                             @ModelAttribute(name = "companyAddBindingModel") CompanyAddBindingModel companyAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("companyAddBindingModel", companyAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyAddBindingModel", bindingResult);
            return "redirect:add";
        }

        CompanyServiceModel companyServiceModel = this.companyService.getCompanyByName(companyAddBindingModel.getName());

        if (companyServiceModel != null) {
            redirectAttributes.addFlashAttribute("companyAddBindingModel", companyAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyAddBindingModel", bindingResult);
            bindingResult.rejectValue("name", "error.companyAddBindingModel", "the name already exists");

            return "redirect:add";
        }

        companyServiceModel = this.companyService.getCompanyByUniqueIdentifier(companyAddBindingModel.getUniqueIdentifier());

        if (companyServiceModel != null) {
            redirectAttributes.addFlashAttribute("companyAddBindingModel", companyAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyAddBindingModel", bindingResult);
            bindingResult.rejectValue("uniqueIdentifier", "error.companyAddBindingModel", "the unique identifier already exists");

            return "redirect:add";
        }

        this.companyService.addCompany(this.modelMapper.map(companyAddBindingModel, CompanyServiceModel.class));

        return "redirect:/company/all";
    }

    @GetMapping("/all")
    @PageTitle("Company all")
    @PreAuthorize("isAuthenticated()")
    public String all(Model model) {

        if (!model.containsAttribute("companies")) {
            List<CompanyViewModel> companyViewModels = this.companyService.getAllCompanies()
                    .stream()
                    .map(companyServiceModel -> this.modelMapper.map(companyServiceModel, CompanyViewModel.class))
                    .collect(Collectors.toList());
            model.addAttribute("companies", companyViewModels);
            model.addAttribute("comparator", Comparator.comparing(CompanyViewModel::getName));
        }

        return "company/all";
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Company edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String edit(@PathVariable String id,
                       Model model) {

        if (!model.containsAttribute("companyEditBindingModel")) {
            CompanyServiceModel companyServiceModel = this.companyService.getCompanyById(id);
            model.addAttribute("companyEditBindingModel", this.modelMapper.map(companyServiceModel, CompanyEditBindingModel.class));
        }

        return "company/edit";
    }

    @PostMapping("/edit/{id}")
//    @PageTitle("Post mapping - edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editConfirm(@PathVariable String id,
                              @Valid @ModelAttribute CompanyEditBindingModel companyEditBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("companyEditBindingModel", companyEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyEditBindingModel", bindingResult);
            return "redirect:{id}";
        }

        CompanyServiceModel companyServiceModel = this.companyService.getCompanyByName(companyEditBindingModel.getName());

        if (companyServiceModel != null && !companyServiceModel.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("companyEditBindingModel", companyEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyEditBindingModel", bindingResult);
            bindingResult.rejectValue("name", "error.companyEditBindingModel", "name already exists");

            return "redirect:{id}";
        }

        companyServiceModel = this.companyService.getCompanyByUniqueIdentifier(companyEditBindingModel.getUniqueIdentifier());

        if (companyServiceModel != null && !companyServiceModel.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("companyEditBindingModel", companyEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyEditBindingModel", bindingResult);
            bindingResult.rejectValue("uniqueIdentifier", "error.companyEditBindingModel", "unique identifier already exists");

            return "redirect:{id}";
        }

        companyServiceModel = this.modelMapper.map(companyEditBindingModel, CompanyServiceModel.class);
        this.companyService.editCompany(companyServiceModel);

        return "redirect:/company/details/{id}";
    }

    @GetMapping("/details/{id}")
    @PageTitle("Company details")
    @PreAuthorize("isAuthenticated()")
    public String details(@PathVariable String id,
                          Model model) {

        if (!model.containsAttribute("companyViewModel")) {
            model.addAttribute("companyViewModel", this.modelMapper.map(this.companyService.getCompanyById(id), CompanyViewModel.class));
        }

        return "company/details";
    }
}
