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

import jakarta.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private static final String COMPANY_ADD_BINDING_MODEL = "companyAddBindingModel";
    private static final String COMPANY_EDIT_BINDING_MODEL = "companyEditBindingModel";
    private static final String VALIDATION_BINDING_RESULT = "org.springframework.validation.BindingResult.";
    private static final String REDIRECT_ADD = "redirect:add";
    private static final String REDIRECT_ID = "redirect:{id}";

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

        if (!model.containsAttribute(COMPANY_ADD_BINDING_MODEL)) {
            model.addAttribute(COMPANY_ADD_BINDING_MODEL, new CompanyAddBindingModel());
        }

        return "company/add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addConfirm(@Valid
                                 @ModelAttribute(name = COMPANY_ADD_BINDING_MODEL) CompanyAddBindingModel companyAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(COMPANY_ADD_BINDING_MODEL, companyAddBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + COMPANY_ADD_BINDING_MODEL, bindingResult);
            return REDIRECT_ADD;
        }

        CompanyServiceModel companyServiceModel = this.companyService.getCompanyByName(companyAddBindingModel.getName());

        if (companyServiceModel != null) {
            redirectAttributes.addFlashAttribute(COMPANY_ADD_BINDING_MODEL, companyAddBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + COMPANY_ADD_BINDING_MODEL, bindingResult);
            bindingResult.rejectValue("name", "error.companyAddBindingModel", "the name already exists");

            return REDIRECT_ADD;
        }

        companyServiceModel = this.companyService.getCompanyByUniqueIdentifier(companyAddBindingModel.getUniqueIdentifier());

        if (companyServiceModel != null) {
            redirectAttributes.addFlashAttribute(COMPANY_ADD_BINDING_MODEL, companyAddBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + COMPANY_ADD_BINDING_MODEL, bindingResult);
            bindingResult.rejectValue("uniqueIdentifier", "error.companyAddBindingModel", "the unique identifier already exists");

            return REDIRECT_ADD;
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

        if (!model.containsAttribute(COMPANY_EDIT_BINDING_MODEL)) {
            CompanyServiceModel companyServiceModel = this.companyService.getCompanyById(id);
            model.addAttribute(COMPANY_EDIT_BINDING_MODEL, this.modelMapper.map(companyServiceModel, CompanyEditBindingModel.class));
        }

        return "company/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editConfirm(@PathVariable String id,
                              @Valid @ModelAttribute CompanyEditBindingModel companyEditBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(COMPANY_EDIT_BINDING_MODEL, companyEditBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + COMPANY_EDIT_BINDING_MODEL, bindingResult);
            return REDIRECT_ID;
        }

        CompanyServiceModel companyServiceModel = this.companyService.getCompanyByName(companyEditBindingModel.getName());

        if (companyServiceModel != null && !companyServiceModel.getId().equals(id)) {
            redirectAttributes.addFlashAttribute(COMPANY_EDIT_BINDING_MODEL, companyEditBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + COMPANY_EDIT_BINDING_MODEL, bindingResult);
            bindingResult.rejectValue("name", "error.companyEditBindingModel", "name already exists");

            return REDIRECT_ID;
        }

        companyServiceModel = this.companyService.getCompanyByUniqueIdentifier(companyEditBindingModel.getUniqueIdentifier());

        if (companyServiceModel != null && !companyServiceModel.getId().equals(id)) {
            redirectAttributes.addFlashAttribute(COMPANY_EDIT_BINDING_MODEL, companyEditBindingModel);
            redirectAttributes.addFlashAttribute(VALIDATION_BINDING_RESULT + COMPANY_EDIT_BINDING_MODEL, bindingResult);
            bindingResult.rejectValue("uniqueIdentifier", "error.companyEditBindingModel", "unique identifier already exists");

            return REDIRECT_ID;
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
