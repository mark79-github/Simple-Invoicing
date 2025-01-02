package bg.softuni.invoice.model.bind;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PRICE_POSITIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemAddBindingModelTest {

    public static final String VALID_NAME = "Valid Item Name";
    public static final BigDecimal VALID_PRICE = BigDecimal.valueOf(20.00);
    public static final String VALID_VAT_VALUE = "20%";
    private static ValidatorFactory validatorFactory;
    private Validator validator;
    private ItemAddBindingModel model;

    @Mock
    private MultipartFile validImageFile;

    @BeforeAll
    static void setupValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @BeforeEach
    void setup() {
        validator = validatorFactory.getValidator();
        model = createValidItemModel();
    }

    private ItemAddBindingModel createValidItemModel() {
        ItemAddBindingModel bindingModel = new ItemAddBindingModel();
        bindingModel.setName(VALID_NAME);
        bindingModel.setPrice(VALID_PRICE);
        bindingModel.setVatValue(VALID_VAT_VALUE);
        bindingModel.setImageUrl(validImageFile);
        return bindingModel;
    }

    @Test
    void name_withValidValue_shouldPassValidation() {
        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_NAME, model.getName());
    }

    @Test
    void name_withTooShortValue_shouldFailValidation() {
        model.setName("A");

        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void name_withNullValue_shouldFailValidation() {
        model.setName(null);

        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void price_withValidValue_shouldPassValidation() {
        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_PRICE, model.getPrice());
    }

    @Test
    void price_withNegativeValue_shouldFailValidation() {
        model.setPrice(BigDecimal.valueOf(-10.00));

        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void price_withNullValue_shouldFailValidation() {
        model.setPrice(null);

        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void vatValue_withValidValue_shouldPassValidation() {
        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_VAT_VALUE, model.getVatValue());
    }

    @Test
    void vatValue_withBlankValue_shouldFailValidation() {
        model.setVatValue(" ");

        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void vatValue_withNullValue_shouldFailValidation() {
        model.setVatValue(null);

        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void image_withValidValue_shouldPassValidation() {
        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validImageFile, model.getImageUrl());
    }

    @Test
    void image_withNullValue_shouldPassValidation() {
        model.setImageUrl(null);

        Set<ConstraintViolation<ItemAddBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
    }
}
