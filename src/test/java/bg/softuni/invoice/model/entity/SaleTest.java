package bg.softuni.invoice.model.entity;

import bg.softuni.invoice.model.enumerated.VatValue;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.PRICE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.PRICE_POSITIVE;
import static bg.softuni.invoice.constant.ErrorMsg.VAT_VALUE_NOT_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaleTest {

    private Validator validator;
    private Sale sale;

    @BeforeEach
    void setUp() {
        validator = createValidator();
        sale = new Sale();
        sale.setName("Product A");
        sale.setQuantity(10);
        sale.setPrice(new BigDecimal("10.50"));
        sale.setVatValue(VatValue.TWENTY);
    }

    // Name tests
    @Test
    void name_withValidValue_shouldPassValidation() {
        sale.setName("Product A");

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);

        assertTrue(violations.isEmpty());
        assertEquals("Product A", sale.getName());
    }

    @Test
    void name_withNullValue_shouldFailValidation() {
        sale.setName(null);

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    // Quantity tests
    @Test
    void quantity_withPositiveValue_shouldPassValidation() {
        sale.setQuantity(10);

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);

        assertTrue(violations.isEmpty());
        assertEquals(10, sale.getQuantity());
    }

    @Test
    void quantity_withZeroOrNegativeValue_shouldFailValidation() {
        sale.setQuantity(0);

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    // Price tests
    @Test
    void price_withPositiveValue_shouldPassValidation() {
        sale.setPrice(new BigDecimal("50.00"));

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);

        assertTrue(violations.isEmpty());
        assertEquals(new BigDecimal("50.00"), sale.getPrice());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1.00", "0.00"})
    void price_withZeroOrNegativeValue_shouldFailValidation(String value) {
        sale.setPrice(new BigDecimal(value));

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void price_withNullValue_shouldFailValidation() {
        sale.setPrice(null);

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // VAT value tests
    @Test
    void vatValue_withValidEnum_shouldPassValidation() {
        sale.setVatValue(VatValue.NINE);

        assertEquals(VatValue.NINE, sale.getVatValue());
    }

    @Test
    void vatValue_withNullValue_shouldFailValidation() {
        sale.setVatValue(null);

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(VAT_VALUE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
