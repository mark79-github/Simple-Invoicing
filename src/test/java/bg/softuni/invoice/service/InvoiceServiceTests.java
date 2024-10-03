package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.InvoiceNotFoundException;
import bg.softuni.invoice.model.entity.Company;
import bg.softuni.invoice.model.entity.Invoice;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.model.entity.Sale;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import bg.softuni.invoice.model.enumerated.VatValue;
import bg.softuni.invoice.model.service.CompanyServiceModel;
import bg.softuni.invoice.model.service.InvoiceServiceModel;
import bg.softuni.invoice.model.service.UserServiceModel;
import bg.softuni.invoice.repository.InvoiceRepository;
import bg.softuni.invoice.service.impl.InvoiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static bg.softuni.invoice.constant.ErrorMsg.INVOICE_NOT_FOUND;
import static bg.softuni.invoice.constant.ErrorMsg.USERNAME_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTests {

    private static final String INVOICE_NON_EXISTING = UUID.randomUUID().toString();
    private static final String USER_ID = UUID.randomUUID().toString();
    private Invoice invoice;
    private final List<Invoice> invoiceList = new ArrayList<>();

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    public void init() {

        invoice = new Invoice();
        invoice.setDate(LocalDate.now());
        invoice.setCreatedOn(LocalDateTime.now());
        invoice.setInvoiceNumber(1);
        invoice.setPaymentType(PaymentType.CASH);
        invoice.setStatusType(StatusType.AWAIT);
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

        assertThatThrownBy(() -> invoiceService.changeStatus(INVOICE_NON_EXISTING))
                .isInstanceOf(InvoiceNotFoundException.class)
                .hasMessage(INVOICE_NOT_FOUND);
    }

    @Test
    void changeStatus_shouldChangeInvoiceStatusCorrectly() {

        doReturn(Optional.ofNullable(invoice)).when(invoiceRepository).findById(anyString());

        invoiceService.changeStatus(INVOICE_NON_EXISTING);

        verify(invoiceRepository, times(1)).saveAndFlush(isA(Invoice.class));
        assertThat(invoice.getStatusType()).isEqualTo(StatusType.COMPLETE);
    }

    @Test
    void getAllInvoices_shouldReturnAllInvoicesCorrectly() {

        doReturn(invoiceList).when(invoiceRepository).findAll();

        List<InvoiceServiceModel> allInvoices = invoiceService.getAllInvoices();

        assertThat(allInvoices).isInstanceOf(List.class).hasSize(1);
    }

    @Test
    void getAllInvoicesByUserId_shouldReturnAllInvoiceByGivenUserCorrectly() {

        UserServiceModel userServiceModel = new UserServiceModel();

        doReturn(invoiceList).when(invoiceRepository).getAllByUser(any(User.class));
        doReturn(userServiceModel).when(userService).getUserById(anyString());

        List<InvoiceServiceModel> invoicesByUser = invoiceService.getAllInvoicesByUserId(USER_ID);

        assertThat(invoicesByUser).isInstanceOf(List.class).hasSize(1);
    }

    @Test
    void getAllInvoicesStatus_shouldReturnAllInvoiceByGivenStatusCorrectly() {

        doReturn(invoiceList).when(invoiceRepository).getAllByStatusType(any(StatusType.class));

        List<InvoiceServiceModel> invoicesByStatus = invoiceService.getAllInvoicesStatus(StatusType.AWAIT);

        assertThat(invoicesByStatus).isInstanceOf(List.class).hasSize(1);
    }

    @Test
    void addInvoice_shouldThrowExceptionIfUserNotExists() {

        String username = "NOT_EXISTING_USER";
        InvoiceServiceModel invoiceServiceModel = new InvoiceServiceModel();
        CompanyServiceModel senderServiceModel = new CompanyServiceModel();
        CompanyServiceModel receiverServiceModel = new CompanyServiceModel();
        invoiceServiceModel.setSender(senderServiceModel);
        invoiceServiceModel.setReceiver(receiverServiceModel);
        invoiceServiceModel.setPaymentType(PaymentType.CASH);

        assertThatThrownBy(() -> invoiceService.addInvoice(invoiceServiceModel, username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage(String.format(USERNAME_NOT_FOUND, username));
    }

    @Test
    void addInvoice_shouldCreateInvoiceCorrectly() {

        String username = "NOT_EXISTING_USER";
        InvoiceServiceModel invoiceServiceModel = new InvoiceServiceModel();
        CompanyServiceModel senderServiceModel = new CompanyServiceModel();
        CompanyServiceModel receiverServiceModel = new CompanyServiceModel();
        invoiceServiceModel.setSender(senderServiceModel);
        invoiceServiceModel.setReceiver(receiverServiceModel);
        invoiceServiceModel.setPaymentType(PaymentType.CASH);
        invoiceServiceModel.setSales(new HashSet<>());
        UserServiceModel userServiceModel = new UserServiceModel();

        doReturn(Optional.of(1L)).when(invoiceRepository).getLastInvoiceNumber();
        doReturn(Optional.of(userServiceModel)).when(userService).getUserByName(username);
        given(invoiceRepository.saveAndFlush(isA(Invoice.class))).willReturn(invoice);

        invoiceService.addInvoice(invoiceServiceModel, username);
        verify(invoiceRepository, times(1)).saveAndFlush(isA(Invoice.class));
    }
}
