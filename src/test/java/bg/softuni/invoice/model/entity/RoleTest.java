package bg.softuni.invoice.model.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.AUTHORITY_NOT_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = createValidator();
    }

    @Test
    void role_withValidConstructorArgument_shouldSetAuthority() {
        Role role = new Role("ROLE_ADMIN");

        assertEquals("ROLE_ADMIN", role.getAuthority());
    }

    @Test
    void authority_withValidValue_shouldReturnCorrectAuthority() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");

        assertEquals("ROLE_USER", role.getAuthority());
    }

    @Test
    void authority_withNullValue_shouldFailValidation() {
        Role role = new Role();
        role.setAuthority(null);

        Set<ConstraintViolation<Role>> violations = validator.validate(role);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(AUTHORITY_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void authority_withEmptyValue_shouldFailValidation() {
        Role role = new Role();
        role.setAuthority("");

        Set<ConstraintViolation<Role>> violations = validator.validate(role);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(AUTHORITY_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    @Test
    void authority_withValidValue_shouldPassValidation() {
        Role role = new Role();
        role.setAuthority("ROLE_MANAGER");

        Set<ConstraintViolation<Role>> violations = validator.validate(role);

        assertTrue(violations.isEmpty());
    }

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
