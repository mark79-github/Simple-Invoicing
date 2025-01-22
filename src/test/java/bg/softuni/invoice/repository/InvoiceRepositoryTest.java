package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Company;
import bg.softuni.invoice.model.entity.Invoice;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InvoiceRepositoryTest {

    private User user;
    private Company sender;
    private Company receiver;

    private final InvoiceRepository invoiceRepository;

    private final EntityManager entityManager;

    @Autowired
    public InvoiceRepositoryTest(InvoiceRepository invoiceRepository, EntityManager entityManager) {
        this.invoiceRepository = invoiceRepository;
        this.entityManager = entityManager;
    }

    @BeforeEach
    void setup() {
        user = persistUser("testuser@example.com", "securepassword", "Test", "User");

        sender = persistCompany(
                "Sender Company",
                "123 Sender Street",
                "123456789",
                true
        );

        receiver = persistCompany(
                "Receiver Company",
                "456 Receiver Avenue",
                "987654321",
                false
        );
    }

    @Test
    void testGetAllByUser_withExistingUser_returnsInvoices() {
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("100.00"), PaymentType.CASH, StatusType.COMPLETE, 1);
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("150.50"), PaymentType.CASH, StatusType.COMPLETE, 2);

        List<Invoice> result = invoiceRepository.getAllByUser(user);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Invoice::getTotalValue)
                .containsExactlyInAnyOrder(new BigDecimal("100.00"), new BigDecimal("150.50"));
    }

    @Test
    void testGetAllByUser_withNoInvoicesForUser_returnsEmptyList() {
        List<Invoice> result = invoiceRepository.getAllByUser(user);

        assertThat(result).isEmpty();
    }

    @Test
    void testGetAllByUser_withDifferentUser_returnsOnlyRelevantInvoices() {
        User user1 = persistUser("user1@example.com", "password1", "UserOne", "Test");
        User user2 = persistUser("user2@example.com", "password2", "UserTwo", "Test");
        persistInvoice(user1, sender, receiver, LocalDate.now(), new BigDecimal("200.00"), PaymentType.TRANSFER, StatusType.COMPLETE, 3);

        List<Invoice> resultForUser1 = invoiceRepository.getAllByUser(user1);
        List<Invoice> resultForUser2 = invoiceRepository.getAllByUser(user2);

        assertThat(resultForUser1).hasSize(1);
        assertThat(resultForUser1.get(0).getTotalValue()).isEqualByComparingTo("200.00");
        assertThat(resultForUser2).isEmpty();
    }

    @Test
    void testGetAllByStatusType_withExistingStatus_returnsInvoices() {
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("100.00"), PaymentType.CASH, StatusType.COMPLETE, 1);
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("150.50"), PaymentType.CASH, StatusType.COMPLETE, 2);
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("50.00"), PaymentType.TRANSFER, StatusType.AWAIT, 3);

        List<Invoice> result = invoiceRepository.getAllByStatusType(StatusType.COMPLETE);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Invoice::getTotalValue)
                .containsExactlyInAnyOrder(new BigDecimal("100.00"), new BigDecimal("150.50"));
    }

    @Test
    void testGetAllByStatusType_withNoInvoicesForStatus_returnsEmptyList() {
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("300.00"), PaymentType.CASH, StatusType.COMPLETE, 4);

        List<Invoice> result = invoiceRepository.getAllByStatusType(StatusType.AWAIT);

        assertThat(result).isEmpty();
    }

    @Test
    void testGetLastInvoiceNumber_withExistingInvoices_returnsMaxInvoiceNumber() {
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("100.00"), PaymentType.CASH, StatusType.COMPLETE, 1);
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("200.00"), PaymentType.CASH, StatusType.COMPLETE, 5);
        persistInvoice(user, sender, receiver, LocalDate.now(), new BigDecimal("300.00"), PaymentType.TRANSFER, StatusType.AWAIT, 3);

        Optional<Long> result = invoiceRepository.getLastInvoiceNumber();

        assertThat(result).isPresent().contains(5L);
    }

    @Test
    void testGetLastInvoiceNumber_withNoInvoices_returnsEmpty() {
        Optional<Long> result = invoiceRepository.getLastInvoiceNumber();

        assertThat(result).isNotPresent();
    }

    private User persistUser(String username, String password, String firstName, String lastName) {
        User createdBy = new User();
        createdBy.setUsername(username);
        createdBy.setPassword(password);
        createdBy.setFirstName(firstName);
        createdBy.setLastName(lastName);
        createdBy.setEnabled(true);
        entityManager.persist(createdBy);
        return createdBy;
    }

    private void persistInvoice(
            User user, Company sender, Company receiver,
            LocalDate date, BigDecimal totalValue,
            PaymentType paymentType, StatusType statusType,
            int invoiceNumber) {
        Invoice invoice = new Invoice();
        invoice.setDate(date);
        invoice.setTotalValue(totalValue);
        invoice.setUser(user);

        invoice.setSender(sender);
        invoice.setReceiver(receiver);

        invoice.setPaymentType(paymentType);
        invoice.setStatusType(statusType);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setCreatedOn(LocalDateTime.now());
        entityManager.persist(invoice);
    }

    private Company persistCompany(String name, String address, String uniqueIdentifier, boolean supplier) {
        Company company = new Company();
        company.setName(name);
        company.setAddress(address);
        company.setUniqueIdentifier(uniqueIdentifier);
        company.setSupplier(supplier);
        entityManager.persist(company);
        return company;
    }
}
