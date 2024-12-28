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

import static bg.softuni.invoice.constant.ErrorMsg.IMAGE_SOURCE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PRICE_POSITIVE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemEditBindingModelTests {

    public static final String VALID_ID = "valid-id";
    public static final String VALID_NAME = "Valid Item Name";
    public static final BigDecimal VALID_PRICE = BigDecimal.valueOf(20.00);
    public static final String VALID_VAT_VALUE = "20%";
    public static final String VALID_IMAGE_URL = "image.png";

    private static ValidatorFactory validatorFactory;
    private Validator validator;
    private ItemEditBindingModel model;

    @Mock
    private MultipartFile validImageFile, newImageFile;

    @BeforeAll
    static void setupValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @BeforeEach
    void setup() {
        validator = validatorFactory.getValidator();
        model = createValidItemModel();
    }

    @Test
    void id_withValidValue_shouldSetAndGetValueCorrectly() {
        final String ID = "id";
        model.setId(ID);

        assertEquals(ID, model.getId());
    }

    @Test
    void name_withValidValue_shouldPassValidation() {
        final String name = "Name";
        model.setName(name);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(name, model.getName());
    }

    @Test
    void name_withTooShortValue_shouldFailValidation() {
        model.setName("A");
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void name_withNullValue_shouldFailValidation() {
        model.setName(null);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void price_withValidValue_shouldPassValidation() {
        BigDecimal price = BigDecimal.valueOf(10.00);
        model.setPrice(price);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(price, model.getPrice());
    }

    @Test
    void price_withNegativeValue_shouldFailValidation() {
        model.setPrice(BigDecimal.valueOf(-10.00));
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void price_withNullValue_shouldFailValidation() {
        model.setPrice(null);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void vatValue_withValidValue_shouldPassValidation() {
        String vatValue = "9%";
        model.setVatValue(vatValue);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(vatValue, model.getVatValue());
    }

    @Test
    void vatValue_withBlankValue_shouldFailValidation() {
        model.setVatValue(" ");
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void vatValue_withNullValue_shouldFailValidation() {
        model.setVatValue(null);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PRICE_POSITIVE, violations.iterator().next().getMessage());
    }

    @Test
    void imageUrl_withValidValue_shouldPassValidation() {
        String image = "image.jpg";
        model.setImageUrl(image);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(image, model.getImageUrl());
    }

    @Test
    void imageUrl_withNullValue_shouldFailValidation() {
        model.setImageUrl(null);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(IMAGE_SOURCE_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void newImageUrl_withNullValue_shouldPassValidation() {
        model.setNewImageUrl(null);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertNull(model.getNewImageUrl());
    }

    @Test
    void newImageUrl_withValidValue_shouldPassValidation() {
        model.setNewImageUrl(newImageFile);
        Set<ConstraintViolation<ItemEditBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(newImageFile, model.getNewImageUrl());
    }

    private ItemEditBindingModel createValidItemModel() {
        ItemEditBindingModel bindingModel = new ItemEditBindingModel();
        bindingModel.setId(VALID_ID);
        bindingModel.setName(VALID_NAME);
        bindingModel.setPrice(VALID_PRICE);
        bindingModel.setVatValue(VALID_VAT_VALUE);
        bindingModel.setImageUrl(VALID_IMAGE_URL);
        bindingModel.setNewImageUrl(validImageFile);
        return bindingModel;
    }
}
