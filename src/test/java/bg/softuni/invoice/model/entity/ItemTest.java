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

import static bg.softuni.invoice.constant.ErrorMsg.IMAGE_SOURCE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PRICE_POSITIVE;
import static bg.softuni.invoice.constant.ErrorMsg.VAT_VALUE_NOT_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemTest {

    private Validator validator;
    private Item item;

    @BeforeEach
    void setUp() {
        validator = createValidator();
        item = new Item();
        item.setName("Product X");
        item.setPrice(new BigDecimal("15.99"));
        item.setImageUrl("http://example.com/image.png");
        item.setVatValue(VatValue.TWENTY);
    }

    // Name tests
    @Test
    void name_withValidValue_shouldPassValidation() {
        item.setName("Product X");

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertTrue(violations.isEmpty());
        assertEquals("Product X", item.getName());
    }

    @Test
    void name_withShortValue_shouldFailValidation() {
        item.setName("Pr");

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void name_withNullValue_shouldFailValidation() {
        item.setName(null);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    // Price tests
    @Test
    void price_withPositiveValue_shouldPassValidation() {
        item.setPrice(new BigDecimal("29.99"));

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertTrue(violations.isEmpty());
        assertEquals(new BigDecimal("29.99"), item.getPrice());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-10.00", "0.00"})
    void price_withZeroOrNegativeValue_shouldFailValidation(String value) {
        item.setPrice(new BigDecimal(value));

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertFalse(violations.isEmpty());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void price_withNullValue_shouldFailValidation() {
        item.setPrice(null);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    // Image URL tests
    @Test
    void imageUrl_withValidValue_shouldPassValidation() {
        item.setImageUrl("http://example.com/image.png");

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertTrue(violations.isEmpty());
        assertEquals("http://example.com/image.png", item.getImageUrl());
    }

    @Test
    void imageUrl_withNullValue_shouldFailValidation() {
        item.setImageUrl(null);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

        assertFalse(violations.isEmpty());
        assertEquals(IMAGE_SOURCE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // VAT value tests
    @Test
    void vatValue_withValidEnum_shouldPassValidation() {
        item.setVatValue(VatValue.ZERO);

        assertEquals(VatValue.ZERO, item.getVatValue());
    }

    @Test
    void vatValue_withNullValue_shouldFailValidation() {
        item.setVatValue(null);

        Set<ConstraintViolation<Item>> violations = validator.validate(item);

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
