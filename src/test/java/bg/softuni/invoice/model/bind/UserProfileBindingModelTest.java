package bg.softuni.invoice.model.bind;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_MIN_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserProfileBindingModelTest {

    private static final String VALID_EMAIL = "user@example.com";
    private static final String EMPTY_EMAIL = "";
    private static final String INVALID_EMAIL = "useratexampledotcom";

    private static final String VALID_FIRST_NAME = "John";
    private static final String FIRST_NAME_LESS_THAN_THREE_CHARACTERS = "Jo";
    private static final String INVALID_FIRST_NAME = "john";

    private static final String VALID_LAST_NAME = "Doe";
    private static final String LAST_NAME_LESS_THAN_THREE_CHARACTERS = "Do";
    private static final String INVALID_LAST_NAME = "doe";

    @Test
    void setUsername_withValidEmail_shouldPassValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(VALID_EMAIL);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertTrue(violations.isEmpty());
    }

    @Test
    void setUsername_withEmptyEmail_shouldFailValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(EMPTY_EMAIL);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());

        List<String> errorMessages = violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        assertTrue(errorMessages.contains(EMAIL_NOT_EMPTY));
        assertTrue(errorMessages.contains(EMAIL_NOT_CORRECT));
    }

    @Test
    void setFirstName_withValidFirstName_shouldPassValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(VALID_EMAIL);
        userProfile.setFirstName(VALID_FIRST_NAME);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertTrue(violations.isEmpty());
    }

    @Test
    void setFirstName_withShortFirstName_shouldFailValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(VALID_EMAIL);
        userProfile.setFirstName(FIRST_NAME_LESS_THAN_THREE_CHARACTERS);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        assertEquals(FIRST_NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void setFirstName_withInvalidFirstNameCharacters_shouldFailValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(VALID_EMAIL);
        userProfile.setFirstName(INVALID_FIRST_NAME);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(FIRST_NAME_FIRST_LETTER_UPPERCASE, violations.iterator().next().getMessage());
    }

    @Test
    void setUsername_withInvalidEmailFormat_shouldFailValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(INVALID_EMAIL);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(EMAIL_NOT_CORRECT, violations.iterator().next().getMessage());
    }

    @Test
    void setLastName_withValidLastName_shouldPassValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(VALID_EMAIL);
        userProfile.setLastName(VALID_LAST_NAME);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertTrue(violations.isEmpty());
    }

    @Test
    void setLastName_withShortLastName_shouldFailValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(VALID_EMAIL);
        userProfile.setLastName(LAST_NAME_LESS_THAN_THREE_CHARACTERS);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(LAST_NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void setLastName_withInvalidLastNameCharacters_shouldFailValidation() {
        UserProfileBindingModel userProfile = new UserProfileBindingModel();
        userProfile.setUsername(VALID_EMAIL);
        userProfile.setLastName(INVALID_LAST_NAME);

        Validator validator = createValidator();
        Set<ConstraintViolation<UserProfileBindingModel>> violations = validator.validate(userProfile);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(LAST_NAME_FIRST_LETTER_UPPERCASE, violations.iterator().next().getMessage());
    }

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
