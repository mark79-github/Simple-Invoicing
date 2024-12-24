package bg.softuni.invoice.model.bind;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserProfileBindingModelTest {

    private static final String VALID_EMAIL = "user@example.com";
    private static final String EMPTY_EMAIL = "";
    private static final String INVALID_EMAIL = "useratexampledotcom";

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
        Iterator<ConstraintViolation<UserProfileBindingModel>> violationIterator = violations.iterator();
        assertEquals(EMAIL_NOT_EMPTY, violationIterator.next().getMessage());
        assertEquals(EMAIL_NOT_CORRECT, violationIterator.next().getMessage());
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

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
