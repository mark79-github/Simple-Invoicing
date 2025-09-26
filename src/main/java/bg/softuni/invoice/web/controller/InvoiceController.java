package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.bind.InvoiceAddBindingModel;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.service.InvoiceServiceModel;
import bg.softuni.invoice.model.service.ItemServiceModel;
import bg.softuni.invoice.model.service.SaleServiceModel;
import bg.softuni.invoice.model.view.CompanyViewSelectModel;
import bg.softuni.invoice.model.view.InvoiceViewModel;
import bg.softuni.invoice.service.CompanyService;
import bg.softuni.invoice.service.InvoiceService;
import bg.softuni.invoice.service.ItemService;
import bg.softuni.invoice.web.annotation.PageTitle;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.GlobalConstants.CART_ITEMS_COUNT;
import static bg.softuni.invoice.constant.GlobalConstants.CART_TOTAL_PRICE;

@Controller
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private static final String INVOICE_ADD_BINDING_MODEL = "invoiceAddBindingModel";
    private static final String REDIRECT_INVOICE_ALL = "redirect:/invoice/all";

    private final CompanyService companyService;
    private final ModelMapper modelMapper;
    private final InvoiceService invoiceService;
    private final ItemService itemService;

    @GetMapping("/add")
    @PageTitle("Invoice add")
    @PreAuthorize("isAuthenticated()")
    public String add(Model model,
                      HttpSession httpSession) {

        if (!model.containsAttribute(INVOICE_ADD_BINDING_MODEL)) {
            InvoiceAddBindingModel invoiceAddBindingModel = new InvoiceAddBindingModel();
            @SuppressWarnings("unchecked")
            Map<String, Integer> cart = (LinkedHashMap<String, Integer>) httpSession.getAttribute("cart");
            if (!cart.isEmpty()) {
                invoiceAddBindingModel.setTotalValue((BigDecimal) httpSession.getAttribute("totalPrice"));
            }
            invoiceAddBindingModel.setDate(LocalDate.now());
            model.addAttribute(INVOICE_ADD_BINDING_MODEL, invoiceAddBindingModel);

            List<CompanyViewSelectModel> senderCompany = this.companyService.getSupplierCompany(true)
                    .stream()
                    .map(companyServiceModel -> this.modelMapper.map(companyServiceModel, CompanyViewSelectModel.class))
                    .toList();

            List<CompanyViewSelectModel> receiverCompanies = this.companyService.getSupplierCompany(false)
                    .stream()
                    .map(companyServiceModel -> this.modelMapper.map(companyServiceModel, CompanyViewSelectModel.class))
                    .toList();

            model.addAttribute("senderCompany", senderCompany);
            model.addAttribute("receiverCompanies", receiverCompanies);
        }

        return "invoice/add";
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addConfirm(@Valid
                             @ModelAttribute(name = INVOICE_ADD_BINDING_MODEL) InvoiceAddBindingModel invoiceAddBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @AuthenticationPrincipal User principal,
                             HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(INVOICE_ADD_BINDING_MODEL, invoiceAddBindingModel);

            List<CompanyViewSelectModel> senderCompany = this.companyService.getSupplierCompany(true)
                    .stream()
                    .map(companyServiceModel -> this.modelMapper.map(companyServiceModel, CompanyViewSelectModel.class))
                    .toList();

            List<CompanyViewSelectModel> receiverCompanies = this.companyService.getSupplierCompany(false)
                    .stream()
                    .map(companyServiceModel -> this.modelMapper.map(companyServiceModel, CompanyViewSelectModel.class))
                    .toList();

            redirectAttributes.addFlashAttribute("senderCompany", senderCompany);
            redirectAttributes.addFlashAttribute("receiverCompanies", receiverCompanies);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.invoiceAddBindingModel", bindingResult);
            return "redirect:add";
        }

        InvoiceServiceModel invoiceServiceModel = this.modelMapper.map(invoiceAddBindingModel, InvoiceServiceModel.class);
        invoiceServiceModel.setSender(this.companyService.getCompanyById(invoiceAddBindingModel.getSender()));
        invoiceServiceModel.setReceiver(this.companyService.getCompanyById(invoiceAddBindingModel.getReceiver()));
        @SuppressWarnings("unchecked")
        Map<String, Integer> cart = (LinkedHashMap<String, Integer>) httpSession.getAttribute("cart");
        if (!cart.isEmpty()) {
            Set<SaleServiceModel> saleServiceModels = cart.entrySet()
                    .stream()
                    .map(stringIntegerEntry -> {
                        SaleServiceModel saleServiceModel = new SaleServiceModel();
                        ItemServiceModel itemServiceModel = this.itemService.getItemById(stringIntegerEntry.getKey());
                        saleServiceModel.setName(itemServiceModel.getName());
                        saleServiceModel.setPrice(itemServiceModel.getPrice());
                        saleServiceModel.setQuantity(stringIntegerEntry.getValue());
                        saleServiceModel.setVatValue(itemServiceModel.getVatValue());
                        return saleServiceModel;
                    }).collect(Collectors.toSet());
            invoiceServiceModel.setSales(saleServiceModels);
        }

        this.invoiceService.addInvoice(invoiceServiceModel, principal.getUsername());

        httpSession.removeAttribute("cart");
        httpSession.removeAttribute(CART_ITEMS_COUNT);
        httpSession.removeAttribute(CART_TOTAL_PRICE);

        return REDIRECT_INVOICE_ALL;
    }

    @GetMapping("/all")
    @PageTitle("Invoice all")
    @PreAuthorize("isAuthenticated()")
    public String all(Model model,
                      @AuthenticationPrincipal User principal) {

        if (!model.containsAttribute("invoices")) {
            List<InvoiceViewModel> invoices;
            if (principal.getAuthorities().size() > 1) {
                invoices = this.invoiceService.getAllInvoices()
                        .stream()
                        .map(invoiceServiceModel -> this.modelMapper.map(invoiceServiceModel, InvoiceViewModel.class))
                        .toList();
            } else {
                invoices = this.invoiceService.getAllInvoicesByUserId(principal.getId())
                        .stream()
                        .map(invoiceServiceModel -> this.modelMapper.map(invoiceServiceModel, InvoiceViewModel.class))
                        .toList();
            }
            model.addAttribute("invoices", invoices);
            model.addAttribute("comparator", Comparator.comparing(InvoiceViewModel::getInvoiceNumber).reversed());
        }

        return "invoice/all";
    }

    @PostMapping("/set-status/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String setDisabled(@PathVariable(name = "id") String id) {
        this.invoiceService.changeStatus(id);

        return REDIRECT_INVOICE_ALL;
    }
}
