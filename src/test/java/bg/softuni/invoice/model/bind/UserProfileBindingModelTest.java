package bg.softuni.invoice.model.bind;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_MIN_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserProfileBindingModelTest {

    public static final String VALID_USERNAME = "test@example.com";
    public static final String VALID_FIRST_NAME = "John";
    public static final String VALID_LAST_NAME = "Doe";

    private static ValidatorFactory validatorFactory;
    private Validator validator;
    private UserProfileBindingModel model;

    @BeforeAll
    static void setupValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @BeforeEach
    void setup() {
        validator = validatorFactory.getValidator();
        model = new UserProfileBindingModel();
    }

    // ID field test
    @Test
    void id_shouldBeProperlySet() {
        String validId = "123e4567-e89b-12d3-a456-426614174000";
        model.setId(validId);

        assertEquals(validId, model.getId());
    }

    @Test
    void id_shouldAllowNullValue() {
        model.setId(null);

        assertNull(model.getId());
    }

    // Username tests
    @ParameterizedTest
    @CsvSource({
            "valid.email@example.com",
            "another.test@email.com"
    })
    void username_withValidValues_shouldPassValidation(String validUsername) {
        model.setUsername(validUsername);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validUsername, model.getUsername());
    }

    @ParameterizedTest
    @CsvSource({
            "' '",
            "plain@",
            "email.com"
    })
    void username_withInvalidValues_shouldFailValidation(String invalidUsername) {
        model.setUsername(invalidUsername);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(EMAIL_NOT_CORRECT, violations.iterator().next().getMessage());
    }

    @Test
    void username_withNullValue_shouldFailValidation() {
        model.setUsername(null);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(EMAIL_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // First name tests
    @ParameterizedTest
    @CsvSource({
            "John",
            "Michael",
            "Anna"
    })
    void firstName_withValidValues_shouldPassValidation(String validFirstName) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(validFirstName);
        model.setLastName(VALID_LAST_NAME);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validFirstName, model.getFirstName());
    }

    @ParameterizedTest
    @CsvSource({
            "' '",
            "jo",
            "1J"
    })
    void firstName_withInvalidValues_shouldFailValidation(String invalidFirstName) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(invalidFirstName);
        model.setLastName(VALID_LAST_NAME);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(FIRST_NAME_MIN_LENGTH)));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(FIRST_NAME_FIRST_LETTER_UPPERCASE)));
    }

    @Test
    void firstName_withNullValue_shouldFailValidation() {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(null);
        model.setLastName(VALID_LAST_NAME);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(FIRST_NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    // Last name tests
    @ParameterizedTest
    @CsvSource({
            "Doe",
            "James",
            "Brown"
    })
    void lastName_withValidValues_shouldPassValidation(String validLastName) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(validLastName);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validLastName, model.getLastName());
    }

    @ParameterizedTest
    @CsvSource({
            "do",
            "B2",
            "' '"
    })
    void lastName_withInvalidValues_shouldFailValidation(String invalidLastName) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(invalidLastName);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(LAST_NAME_MIN_LENGTH)));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(LAST_NAME_FIRST_LETTER_UPPERCASE)));
    }

    @Test
    void lastName_withNullValue_shouldFailValidation() {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(null);

        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(LAST_NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }
}
