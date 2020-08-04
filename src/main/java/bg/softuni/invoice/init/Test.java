package bg.softuni.invoice.init;

import bg.softuni.invoice.model.entity.*;
import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import bg.softuni.invoice.model.service.CompanyServiceModel;
import bg.softuni.invoice.model.service.ItemServiceModel;
import bg.softuni.invoice.model.service.UserServiceModel;
import bg.softuni.invoice.repository.InvoiceRepository;
import bg.softuni.invoice.service.CompanyService;
import bg.softuni.invoice.service.ItemService;
import bg.softuni.invoice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Test implements CommandLineRunner {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    private final ItemService itemService;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public Test(UserService userService, ModelMapper modelMapper, CompanyService companyService, ItemService itemService, InvoiceRepository invoiceRepository) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.companyService = companyService;
        this.itemService = itemService;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Invoice invoice = new Invoice();

        invoice.setStatusType(StatusType.AWAIT);
        invoice.setCreatedOn(LocalDateTime.now());
        invoice.setInvoiceNumber(1111);
        invoice.setDate(LocalDate.now());
        invoice.setPaymentType(PaymentType.TRANSFER);
        invoice.setTotalValue(BigDecimal.TEN);

        UserServiceModel userServiceModel = this.userService.getUserByName("admin@admin.com").orElse(null);
        User user = this.modelMapper.map(userServiceModel, User.class);
        invoice.setUser(user);

        CompanyServiceModel companyServiceModel = this.companyService.getCompanyByName("123");
        Company companySender = this.modelMapper.map(companyServiceModel, Company.class);
        invoice.setSender(companySender);

        companyServiceModel = this.companyService.getCompanyByName("qwerty");
        Company companyReceiver = this.modelMapper.map(companyServiceModel, Company.class);
        invoice.setReceiver(companyReceiver);

        Sale sale = new Sale();
        ItemServiceModel itemServiceModel = this.itemService.getItemByName("asd");

        sale.setName(itemServiceModel.getName());
        sale.setQuantity(2);
        invoice.getSales().add(sale);

//        this.invoiceRepository.saveAndFlush(invoice);

        List<Invoice> invoices = this.invoiceRepository.getAllByUser(user);
//        BigDecimal invoicePrice = invoices.stream()
//                .map(Invoice::getSales)
//                .map(sales -> {
//                    return sales.stream()
//                            .map(s -> {
//                                return s.getPrice().multiply(BigDecimal.valueOf(s.getQuantity()));
//                            })
//                            .reduce(BigDecimal.ZERO, BigDecimal::add);
//                }).reduce(BigDecimal.ZERO, BigDecimal::add);

//        System.out.println(invoicePrice);


        System.out.println();
    }
}
