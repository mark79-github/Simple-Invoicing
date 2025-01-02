package bg.softuni.invoice.model.view;

import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InvoiceViewModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174009";
        invoiceViewModel.setId(expectedId);

        String actualId = invoiceViewModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        String actualId = invoiceViewModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setId(null);

        String actualId = invoiceViewModel.getId();

        assertNull(actualId);
    }

    // Date tests
    @Test
    void date_withValidValue_shouldReturnCorrectDate() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        LocalDate expectedDate = LocalDate.of(2023, 12, 25);
        invoiceViewModel.setDate(expectedDate);

        LocalDate actualDate = invoiceViewModel.getDate();

        assertEquals(expectedDate, actualDate);
    }

    @Test
    void date_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        LocalDate actualDate = invoiceViewModel.getDate();

        assertNull(actualDate);
    }

    @Test
    void date_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setDate(null);

        LocalDate actualDate = invoiceViewModel.getDate();

        assertNull(actualDate);
    }

    // Total value tests
    @Test
    void totalValue_withValidValue_shouldReturnCorrectTotalValue() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        BigDecimal expectedTotalValue = new BigDecimal("1234.99");
        invoiceViewModel.setTotalValue(expectedTotalValue);

        BigDecimal actualTotalValue = invoiceViewModel.getTotalValue();

        assertEquals(expectedTotalValue, actualTotalValue);
    }

    @Test
    void totalValue_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        BigDecimal actualTotalValue = invoiceViewModel.getTotalValue();

        assertNull(actualTotalValue);
    }

    @Test
    void totalValue_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setTotalValue(null);

        BigDecimal actualTotalValue = invoiceViewModel.getTotalValue();

        assertNull(actualTotalValue);
    }

    // User tests
    @Test
    void user_withValidValue_shouldReturnCorrectUser() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        UserViewModel expectedUser = new UserViewModel();
        expectedUser.setId("111");
        invoiceViewModel.setUser(expectedUser);

        UserViewModel actualUser = invoiceViewModel.getUser();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void user_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        UserViewModel actualUser = invoiceViewModel.getUser();

        assertNull(actualUser);
    }

    @Test
    void user_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setUser(null);

        UserViewModel actualUser = invoiceViewModel.getUser();

        assertNull(actualUser);
    }

    // CreatedOn tests
    @Test
    void createdOn_withValidValue_shouldReturnCorrectCreatedOn() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        LocalDateTime expectedCreatedOn = LocalDateTime.of(2023, 12, 25, 10, 15, 30);
        invoiceViewModel.setCreatedOn(expectedCreatedOn);

        LocalDateTime actualCreatedOn = invoiceViewModel.getCreatedOn();

        assertEquals(expectedCreatedOn, actualCreatedOn);
    }

    @Test
    void createdOn_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        LocalDateTime actualCreatedOn = invoiceViewModel.getCreatedOn();

        assertNull(actualCreatedOn);
    }

    @Test
    void createdOn_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setCreatedOn(null);

        LocalDateTime actualCreatedOn = invoiceViewModel.getCreatedOn();

        assertNull(actualCreatedOn);
    }

    // Sales tests
    @Test
    void sales_withValidValue_shouldReturnCorrectSales() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        Set<SaleViewModel> expectedSales = new HashSet<>();
        SaleViewModel sale = new SaleViewModel();
        sale.setId("sale-1");
        expectedSales.add(sale);
        invoiceViewModel.setSales(expectedSales);

        Set<SaleViewModel> actualSales = invoiceViewModel.getSales();

        assertEquals(expectedSales, actualSales);
    }

    @Test
    void sales_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        Set<SaleViewModel> actualSales = invoiceViewModel.getSales();

        assertNull(actualSales);
    }

    @Test
    void sales_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setSales(null);

        Set<SaleViewModel> actualSales = invoiceViewModel.getSales();

        assertNull(actualSales);
    }


    // Sender tests
    @Test
    void sender_withValidValue_shouldReturnCorrectSender() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        CompanyViewModel expectedSender = new CompanyViewModel();
        expectedSender.setId("1");
        expectedSender.setName("Sender Company");
        invoiceViewModel.setSender(expectedSender);

        CompanyViewModel actualSender = invoiceViewModel.getSender();

        assertEquals(expectedSender, actualSender);
    }

    @Test
    void sender_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        CompanyViewModel actualSender = invoiceViewModel.getSender();

        assertNull(actualSender);
    }

    @Test
    void sender_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setSender(null);

        CompanyViewModel actualSender = invoiceViewModel.getSender();

        assertNull(actualSender);
    }

    // Receiver tests
    @Test
    void receiver_withValidValue_shouldReturnCorrectReceiver() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        CompanyViewModel expectedReceiver = new CompanyViewModel();
        expectedReceiver.setId("2");
        expectedReceiver.setName("Receiver Company");
        invoiceViewModel.setReceiver(expectedReceiver);

        CompanyViewModel actualReceiver = invoiceViewModel.getReceiver();

        assertEquals(expectedReceiver, actualReceiver);
    }

    @Test
    void receiver_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        CompanyViewModel actualReceiver = invoiceViewModel.getReceiver();

        assertNull(actualReceiver);
    }

    @Test
    void receiver_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setReceiver(null);

        CompanyViewModel actualReceiver = invoiceViewModel.getReceiver();

        assertNull(actualReceiver);
    }

    // PaymentType tests
    @Test
    void paymentType_withValidValue_shouldReturnCorrectPaymentType() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        PaymentType expectedPaymentType = PaymentType.CASH;
        invoiceViewModel.setPaymentType(expectedPaymentType);

        PaymentType actualPaymentType = invoiceViewModel.getPaymentType();

        assertEquals(expectedPaymentType, actualPaymentType);
    }

    @Test
    void paymentType_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        PaymentType actualPaymentType = invoiceViewModel.getPaymentType();

        assertNull(actualPaymentType);
    }

    @Test
    void paymentType_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setPaymentType(null);

        PaymentType actualPaymentType = invoiceViewModel.getPaymentType();

        assertNull(actualPaymentType);
    }

    // StatusType tests
    @Test
    void statusType_withValidValue_shouldReturnCorrectStatusType() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        StatusType expectedStatusType = StatusType.COMPLETE;
        invoiceViewModel.setStatusType(expectedStatusType);

        StatusType actualStatusType = invoiceViewModel.getStatusType();

        assertEquals(expectedStatusType, actualStatusType);
    }

    @Test
    void statusType_whenNotSet_shouldReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        StatusType actualStatusType = invoiceViewModel.getStatusType();

        assertNull(actualStatusType);
    }

    @Test
    void statusType_withNullValue_shouldSetAndReturnNull() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setStatusType(null);

        StatusType actualStatusType = invoiceViewModel.getStatusType();

        assertNull(actualStatusType);
    }

    // Invoice number tests
    @Test
    void invoiceNumber_withValidValue_shouldReturnCorrectInvoiceNumber() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        long expectedInvoiceNumber = 123456789L;
        invoiceViewModel.setInvoiceNumber(expectedInvoiceNumber);

        long actualInvoiceNumber = invoiceViewModel.getInvoiceNumber();

        assertEquals(expectedInvoiceNumber, actualInvoiceNumber);
    }

    @Test
    void invoiceNumber_whenNotSet_shouldReturnDefaultValue() {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();

        long actualInvoiceNumber = invoiceViewModel.getInvoiceNumber();

        assertEquals(0L, actualInvoiceNumber); // Default value for long is 0
    }
}
