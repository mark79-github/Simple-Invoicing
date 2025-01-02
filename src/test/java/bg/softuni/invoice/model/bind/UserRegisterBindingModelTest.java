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

import java.util.Iterator;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_NOT_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRegisterBindingModelTest {

    public static final String VALID_USERNAME = "test@example.com";
    public static final String VALID_FIRST_NAME = "John";
    public static final String VALID_LAST_NAME = "Doe";
    public static final String VALID_PASSWORD = "password123";

    private static ValidatorFactory validatorFactory;
    private Validator validator;
    private UserRegisterBindingModel model;

    @BeforeAll
    static void setupValidatorFactory() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    @BeforeEach
    void setup() {
        validator = validatorFactory.getValidator();
        model = new UserRegisterBindingModel();
    }

    @ParameterizedTest
    @CsvSource({
            "valid.email@example.com",
            "another.test@email.com"
    })
    void username_withValidValues_shouldPassValidation(String validUsername) {
        model.setUsername(validUsername);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validUsername, model.getUsername());
    }

    @ParameterizedTest
    @CsvSource({
            "' '",
            "invalid@",
            "plainaddress"
    })
    void username_withInvalidValues_shouldFailValidation(String invalidUsername) {
        model.setUsername(invalidUsername);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(EMAIL_NOT_CORRECT, violations.iterator().next().getMessage());
    }

    @Test
    void username_withNullValue_shouldFailValidation() {
        model.setUsername(null);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(EMAIL_NOT_EMPTY, violations.iterator().next().getMessage());
    }

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
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validFirstName, model.getFirstName());
    }

    @ParameterizedTest
    @CsvSource({
            "' '",
            "J@",
            "jo"
    })
    void firstName_withInvalidValues_shouldFailValidation(String invalidFirstName) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(invalidFirstName);
        model.setLastName(VALID_LAST_NAME);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

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
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(FIRST_NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Doe",
            "White",
            "Smith"
    })
    void lastName_withValidValues_shouldPassValidation(String validLastName) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(validLastName);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validLastName, model.getLastName());
    }

    @ParameterizedTest
    @CsvSource({
            "' '",
            "D2",
            "sm"
    })
    void lastName_withInvalidValues_shouldFailValidation(String invalidLastName) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(invalidLastName);
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

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
        model.setPassword(VALID_PASSWORD);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);
        Iterator<ConstraintViolation<UserRegisterBindingModel>> violationIterator = violations.iterator();

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(LAST_NAME_MIN_LENGTH, violationIterator.next().getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "'password123'",
            "'MyPassword!'"
    })
    void password_withValidValues_shouldPassValidation(String validPassword) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);
        model.setPassword(validPassword);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(validPassword, model.getPassword());
    }

    @ParameterizedTest
    @CsvSource({
            "' '",
            "'pa'"
    })
    void password_withInvalidValues_shouldFailValidation(String invalidPassword) {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);
        model.setPassword(invalidPassword);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PASSWORD_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void password_withNullValue_shouldFailValidation() {
        model.setUsername(VALID_USERNAME);
        model.setFirstName(VALID_FIRST_NAME);
        model.setLastName(VALID_LAST_NAME);
        model.setPassword(null);

        Set<ConstraintViolation<UserRegisterBindingModel>> violations = validator.validate(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(PASSWORD_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "password123",
            "SomeOtherPassword",
            "' '"
    })
    void confirmPassword_shouldBeProperlySet(String confirmPassword) {
        model.setConfirmPassword(confirmPassword);

        assertEquals(confirmPassword, model.getConfirmPassword());
    }

    @Test
    void confirmPassword_shouldAllowNullValue() {
        model.setConfirmPassword(null);

        assertNull(model.getConfirmPassword());
    }
}
