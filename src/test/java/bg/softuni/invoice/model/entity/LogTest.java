package bg.softuni.invoice.model.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.DATE_PAST;
import static bg.softuni.invoice.constant.ErrorMsg.METHOD_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.URI_NOT_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogTest {

    private Validator validator;
    private Log log;

    @BeforeEach
    void setUp() {
        validator = createValidator();

        log = new Log();
        log.setRequestURI("/api/resource");
        log.setMethod("POST");
        log.setDateTime(LocalDateTime.now());
    }

    @Test
    void requestURI_withValidValue_shouldReturnCorrectRequestURI() {
        String expectedRequestURI = "/api/resource";
        log.setRequestURI(expectedRequestURI);

        String actualRequestURI = log.getRequestURI();

        assertEquals(expectedRequestURI, actualRequestURI);
    }

    @Test
    void requestURI_withNullValue_shouldAllowNullValue() {
        log.setRequestURI(null);

        String actualRequestURI = log.getRequestURI();

        assertNull(actualRequestURI);
    }

    @Test
    void method_withValidValue_shouldReturnCorrectMethod() {
        String expectedMethod = "GET";
        log.setMethod(expectedMethod);

        String actualMethod = log.getMethod();

        assertEquals(expectedMethod, actualMethod);
    }

    @Test
    void dateTime_withValidPastOrPresentValue_shouldReturnCorrectDateTime() {
        LocalDateTime expectedDateTime = LocalDateTime.now();
        log.setDateTime(expectedDateTime);

        LocalDateTime actualDateTime = log.getDateTime();

        assertEquals(expectedDateTime, actualDateTime);
    }

    @Test
    void dateTime_withFutureValue_shouldFailValidation() {
        log.setDateTime(LocalDateTime.now().plusDays(1));

        Set<ConstraintViolation<Log>> violations = validator.validate(log);

        assertFalse(violations.isEmpty());
        ConstraintViolation<Log> violation = violations.iterator().next();

        assertEquals(DATE_PAST, violation.getMessage());
    }

    @Test
    void user_withValidValue_shouldReturnCorrectUser() {
        User user = new User();
        user.setUsername("testuser");
        log.setUser(user);

        User actualUser = log.getUser();

        assertEquals(user, actualUser);
        assertEquals("testuser", actualUser.getUsername());
    }

    @Test
    void user_withNullValue_shouldAllowNullValue() {
        log.setUser(null);

        User actualUser = log.getUser();

        assertNull(actualUser);
    }

    @Test
    void log_withValidFields_shouldPassValidation() {
        log.setRequestURI("/api/resource");
        log.setMethod("POST");
        log.setDateTime(LocalDateTime.now());

        Set<ConstraintViolation<Log>> violations = validator.validate(log);

        assertTrue(violations.isEmpty());
    }

    @Test
    void log_withNullRequestURI_shouldFailValidation() {
        log.setRequestURI(null);
        log.setMethod("POST");
        log.setDateTime(LocalDateTime.now());

        Set<ConstraintViolation<Log>> violations = validator.validate(log);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(URI_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void log_withNullMethod_shouldFailValidation() {
        log.setRequestURI("/api/resource");
        log.setMethod(null);
        log.setDateTime(LocalDateTime.now());

        Set<ConstraintViolation<Log>> violations = validator.validate(log);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(METHOD_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
