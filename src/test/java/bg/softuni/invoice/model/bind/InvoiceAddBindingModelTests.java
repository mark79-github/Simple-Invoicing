package bg.softuni.invoice.model.bind;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.DATE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.DATE_NOW_OR_FUTURE;
import static bg.softuni.invoice.constant.ErrorMsg.PAYMENT_TYPE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.RECEIVER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.SENDER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.VALUE_POSITIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvoiceAddBindingModelTests {

    public static final String VALID_SENDER = "Valid Sender";
    public static final String VALID_RECEIVER = "Valid Receiver";
    public static final String VALID_PAYMENT_TYPE = "Valid Payment Type";
    public static final BigDecimal VALID_TOTAL_VALUE = BigDecimal.valueOf(100.50);
    public static final LocalDate VALID_DATE = LocalDate.now().plusDays(5);

    private static ValidatorFactory validatorFactory;
    private Validator validator;
    private InvoiceAddBindingModel model;

    @BeforeAll
    static void setupValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @BeforeEach
    void setup() {
        validator = validatorFactory.getValidator();
        model = createValidInvoiceModel();
    }

    @Test
    void sender_withValidValue_shouldPassValidation() {
        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_SENDER, model.getSender());
    }

    @Test
    void sender_withBlankValue_shouldFailValidation() {
        model.setSender(" ");

        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(SENDER_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void receiver_withValidValue_shouldPassValidation() {
        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_RECEIVER, model.getReceiver());
    }

    @Test
    void receiver_withBlankValue_shouldFailValidation() {
        model.setReceiver(" ");

        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(RECEIVER_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void paymentType_withValidValue_shouldPassValidation() {
        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_PAYMENT_TYPE, model.getPaymentType());
    }

    @Test
    void paymentType_withBlankValue_shouldFailValidation() {
        model.setPaymentType(" ");

        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PAYMENT_TYPE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void totalValue_withPositiveValue_shouldPassValidation() {
        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_TOTAL_VALUE, model.getTotalValue());
    }

    @Test
    void totalValue_withNegativeValue_shouldFailValidation() {
        model.setTotalValue(BigDecimal.valueOf(-50.00));

        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(VALUE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void totalValue_withNullValue_shouldFailValidation() {
        model.setTotalValue(null);

        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(VALUE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void date_withFutureValue_shouldPassValidation() {
        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_DATE, model.getDate());
    }

    @Test
    void date_withPastValue_shouldFailValidation() {
        model.setDate(LocalDate.now().minusDays(5));

        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(DATE_NOW_OR_FUTURE, violations.iterator().next().getMessage());
    }

    @Test
    void date_withNullValue_shouldFailValidation() {
        model.setDate(null);

        Set<ConstraintViolation<InvoiceAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(DATE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    private InvoiceAddBindingModel createValidInvoiceModel() {
        InvoiceAddBindingModel bindingModel = new InvoiceAddBindingModel();
        bindingModel.setSender(VALID_SENDER);
        bindingModel.setReceiver(VALID_RECEIVER);
        bindingModel.setPaymentType(VALID_PAYMENT_TYPE);
        bindingModel.setTotalValue(VALID_TOTAL_VALUE);
        bindingModel.setDate(VALID_DATE);
        return bindingModel;
    }
}
