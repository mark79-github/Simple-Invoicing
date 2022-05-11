package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.InvoiceNotFoundException;
import bg.softuni.invoice.model.entity.*;
import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import bg.softuni.invoice.model.enumerated.VatValue;
import bg.softuni.invoice.repository.InvoiceRepository;
import bg.softuni.invoice.service.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InvoiceServiceTests {

    private final String INVOICE_NON_EXISTING = UUID.randomUUID().toString();

    private final List<Invoice> invoiceList = new ArrayList<>();

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {

        this.invoiceService = new InvoiceServiceImpl(invoiceRepository, modelMapper, userService);

        Invoice invoice = new Invoice();
        invoice.setDate(LocalDate.now());
        invoice.setCreatedOn(LocalDateTime.now());
        invoice.setInvoiceNumber(1);
        invoice.setPaymentType(PaymentType.CASH);
        invoice.setStatusType(StatusType.COMPLETE);
        invoice.setTotalValue(BigDecimal.TEN);

        Company company = new Company();
        company.setName("company");
        company.setAddress("address");
        company.setUniqueIdentifier("123456789");
        company.setSupplier(true);

        User user = new User();
        user.setUsername("admin@admin.com");
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setPassword("admin");
        user.setAuthorities(Set.of(new Role("ROLE_ROOT")));

        Sale sale = new Sale();
        sale.setName("item");
        sale.setPrice(BigDecimal.TEN);
        sale.setQuantity(1);
        sale.setVatValue(VatValue.TWENTY);

        invoice.setReceiver(company);
        invoice.setSender(company);
        invoice.setUser(user);
        invoice.setSales(Set.of(sale));

        this.invoiceList.add(invoice);
    }

    @Test
    void changeStatus_shouldThrowExceptionIfInvoiceNotExists() {

        Assertions.assertThrows(InvoiceNotFoundException.class, () -> this.invoiceService.changeStatus(INVOICE_NON_EXISTING));
    }
}
