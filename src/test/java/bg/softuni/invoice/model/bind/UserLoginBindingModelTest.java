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
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_NOT_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserLoginBindingModelTest {

    public static final String VALID_USERNAME = "test@example.com";
    public static final String VALID_PASSWORD = "password123";

    private static ValidatorFactory validatorFactory;
    private Validator validator;
    private UserLoginBindingModel model;

    @BeforeAll
    static void setupValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @BeforeEach
    void setup() {
        validator = validatorFactory.getValidator();
        model = new UserLoginBindingModel();
    }

    @ParameterizedTest
    @CsvSource({
            "valid.email@example.com",
            "another.test@email.com"
    })
    void username_withValidValues_shouldPassValidation(String validUsername) {
        model.setUsername(validUsername);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserLoginBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validUsername, model.getUsername());
    }

    @Test
    void username_withEmptyValue_shouldFailValidation() {
        model.setUsername(" ");
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserLoginBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(EMAIL_NOT_EMPTY)));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(EMAIL_NOT_CORRECT)));
    }

    @Test
    void username_withNullValue_shouldFailValidation() {
        model.setUsername(null);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserLoginBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(EMAIL_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "invalid-email",
            "invalid@domain",
            "plainaddress",
            "@missing-localpart.com"
    })
    void username_withInvalidEmailPatterns_shouldFailValidation(String invalidEmail) {
        model.setUsername(invalidEmail);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserLoginBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(EMAIL_NOT_CORRECT, violations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "validPassword1",
            "123StrongPass",
            "another#Pass!"
    })
    void password_withValidValues_shouldPassValidation(String validPassword) {
        model.setUsername(VALID_USERNAME);
        model.setPassword(validPassword);

        Set<ConstraintViolation<UserLoginBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validPassword, model.getPassword());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void password_withNullOrEmptyValues_shouldFailValidation(String invalidPassword) {
        model.setUsername(VALID_USERNAME);
        model.setPassword(invalidPassword);

        Set<ConstraintViolation<UserLoginBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PASSWORD_NOT_EMPTY, violations.iterator().next().getMessage());
    }
}
