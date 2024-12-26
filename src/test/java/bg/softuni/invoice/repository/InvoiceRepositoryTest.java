package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Invoice;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testGetAllByUser_withExistingUser_returnsInvoices() {
        User user = persistUser("testuser@example.com", "securepassword", "Test", "User", true);
        persistInvoice(user, LocalDate.now(), new BigDecimal("100.00"), PaymentType.CASH, StatusType.COMPLETE, 1);
        persistInvoice(user, LocalDate.now(), new BigDecimal("150.50"), PaymentType.CASH, StatusType.COMPLETE, 2);

        List<Invoice> result = invoiceRepository.getAllByUser(user);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Invoice::getTotalValue)
                .containsExactlyInAnyOrder(new BigDecimal("100.00"), new BigDecimal("150.50"));
    }

    @Test
    void testGetAllByUser_withNoInvoicesForUser_returnsEmptyList() {
        User user = persistUser("nouser@example.com", "securepassword", "No", "Invoices", true);

        List<Invoice> result = invoiceRepository.getAllByUser(user);

        assertThat(result).isEmpty();
    }

    @Test
    void testGetAllByUser_withDifferentUser_returnsOnlyRelevantInvoices() {
        User user1 = persistUser("user1@example.com", "password1", "User1", "Test", true);
        User user2 = persistUser("user2@example.com", "password2", "User2", "Test", true);
        persistInvoice(user1, LocalDate.now(), new BigDecimal("200.00"), PaymentType.TRANSFER, StatusType.COMPLETE, 3);

        List<Invoice> resultForUser1 = invoiceRepository.getAllByUser(user1);
        List<Invoice> resultForUser2 = invoiceRepository.getAllByUser(user2);

        assertThat(resultForUser1).hasSize(1);
        assertThat(resultForUser1.get(0).getTotalValue()).isEqualByComparingTo("200.00");
        assertThat(resultForUser2).isEmpty();
    }

    private User persistUser(String username, String password, String firstName, String lastName, boolean enabled) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(enabled);
        entityManager.persist(user);
        return user;
    }

    private Invoice persistInvoice(User user, LocalDate date, BigDecimal totalValue, PaymentType paymentType, StatusType statusType, int invoiceNumber) {
        Invoice invoice = new Invoice();
        invoice.setDate(date);
        invoice.setTotalValue(totalValue);
        invoice.setUser(user);
        invoice.setSender(null);
        invoice.setReceiver(null);
        invoice.setPaymentType(paymentType);
        invoice.setStatusType(statusType);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setCreatedOn(LocalDateTime.now());
        entityManager.persist(invoice);
        return invoice;
    }
}
