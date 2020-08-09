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
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
//@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTests {

    private final String INVOICE_NON_EXISTING = UUID.randomUUID().toString();

    private List<Invoice> invoiceList = new ArrayList<>();
    private Invoice invoice;
    private Company company;
    private User user;
    private Sale sale;

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

        this.invoice = new Invoice();
        invoice.setDate(LocalDate.now());
        invoice.setCreatedOn(LocalDateTime.now());
        invoice.setInvoiceNumber(1);
        invoice.setPaymentType(PaymentType.CASH);
        invoice.setStatusType(StatusType.COMPLETE);
        invoice.setTotalValue(BigDecimal.TEN);

        this.company = new Company();
        company.setName("company");
        company.setAddress("address");
        company.setUniqueIdentifier("123456789");
        company.setSupplier(true);

        this.user = new User();
        this.user.setUsername("admin@admin.com");
        this.user.setFirstName("Admin");
        this.user.setLastName("Admin");
        this.user.setPassword("admin");
        this.user.setAuthorities(Set.of(new Role("ROLE_ROOT")));

        this.sale = new Sale();
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
    public void changeStatus_shouldThrowExceptionIfInvoiceNotExists() {

        Assertions.assertThrows(InvoiceNotFoundException.class, () -> this.invoiceService.changeStatus(INVOICE_NON_EXISTING));
    }
}
