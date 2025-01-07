package bg.softuni.invoice.model.entity;

import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.DATE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.INVOICE_NUMBER_POSITIVE;
import static bg.softuni.invoice.constant.ErrorMsg.PAYMENT_TYPE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.RECEIVER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.SENDER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.STATUS_TYPE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.USER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.VALUE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.VALUE_POSITIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvoiceTest {

    private Validator validator;
    private Invoice invoice;

    @BeforeEach
    void setUp() {
        validator = createValidator();

        invoice = new Invoice();
        invoice.setDate(LocalDate.now());
        invoice.setTotalValue(new BigDecimal("100.00"));
        invoice.setUser(new User());
        invoice.setSender(new Company());
        invoice.setReceiver(new Company());
        invoice.setPaymentType(PaymentType.CASH);
        invoice.setStatusType(StatusType.COMPLETE);
        invoice.setInvoiceNumber(123456789);
        invoice.setCreatedOn(LocalDateTime.now());
    }

    // Date tests
    @Test
    void date_withValidValue_shouldPassValidation() {
        LocalDate now = LocalDate.now();
        invoice.setDate(now);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertEquals(now, invoice.getDate());
    }

    @Test
    void date_withNullValue_shouldFailValidation() {
        invoice.setDate(null);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(DATE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // TotalValue tests
    @Test
    void totalValue_withPositiveValue_shouldPassValidation() {
        invoice.setTotalValue(new BigDecimal("150.50"));

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertEquals(new BigDecimal("150.50"), invoice.getTotalValue());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.00, -50.00})
    void totalValue_withZeroOrNegativeValue_shouldFailValidation(double value) {
        invoice.setTotalValue(new BigDecimal(value));

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(VALUE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void totalValue_withNullValue_shouldFailValidation() {
        invoice.setTotalValue(null);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(VALUE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // User tests
    @Test
    void user_withValidValue_shouldPassValidation() {
        invoice.setUser(new User());

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertNotNull(invoice.getUser());
    }

    @Test
    void user_withNullValue_shouldFailValidation() {
        invoice.setUser(null);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(USER_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // Sender tests
    @Test
    void sender_withValidValue_shouldPassValidation() {
        invoice.setSender(new Company());

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertNotNull(invoice.getSender());
    }

    @Test
    void sender_withNullValue_shouldFailValidation() {
        invoice.setSender(null);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(SENDER_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // Receiver tests
    @Test
    void receiver_withValidValue_shouldPassValidation() {
        invoice.setReceiver(new Company());

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertNotNull(invoice.getReceiver());
    }

    @Test
    void receiver_withNullValue_shouldFailValidation() {
        invoice.setReceiver(null);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(RECEIVER_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // PaymentType tests
    @Test
    void paymentType_withValidEnum_shouldPassValidation() {
        invoice.setPaymentType(PaymentType.CASH);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertEquals(PaymentType.CASH, invoice.getPaymentType());
    }

    @Test
    void paymentType_withNullValue_shouldFailValidation() {
        invoice.setPaymentType(null);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PAYMENT_TYPE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // StatusType tests
    @Test
    void statusType_withValidEnum_shouldPassValidation() {
        invoice.setStatusType(StatusType.AWAIT);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertEquals(StatusType.AWAIT, invoice.getStatusType());
    }

    @Test
    void statusType_withNullValue_shouldFailValidation() {
        invoice.setStatusType(null);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(STATUS_TYPE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // InvoiceNumber tests
    @Test
    void invoiceNumber_withValidValue_shouldPassValidation() {
        invoice.setInvoiceNumber(123456789);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertEquals(123456789, invoice.getInvoiceNumber());
    }

    @Test
    void invoiceNumber_withZeroOrNegativeValue_shouldFailValidation() {
        invoice.setInvoiceNumber(-123456);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(INVOICE_NUMBER_POSITIVE, violations.iterator().next().getMessage());
    }

    // CreatedOn tests
    @Test
    void createdOn_withValidValue_shouldPassValidation() {
        invoice.setCreatedOn(LocalDateTime.now());

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertTrue(violations.isEmpty());
        assertNotNull(invoice.getCreatedOn());
    }

    @Test
    void createdOn_withNullValue_shouldFailValidation() {
        invoice.setCreatedOn(null);

        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(DATE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
