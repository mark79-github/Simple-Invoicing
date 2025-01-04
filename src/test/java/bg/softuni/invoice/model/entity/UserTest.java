package bg.softuni.invoice.model.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_MIN_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private Validator validator;
    private User user;

    @BeforeEach
    void setUp() {
        validator = createValidator();
        user = new User();
        user.setUsername("test@example.com");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setPassword("<PASSWORD>");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        Role role1 = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");
        Set<Role> roles = Set.of(role1, role2);
        user.setAuthorities(roles);
    }

    // Username tests
    @Test
    void username_withValidEmail_shouldPassValidation() {
        user.setUsername("test@example.com");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void username_withInvalidEmail_shouldFailValidation() {
        user.setUsername("invalid-email");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(EMAIL_NOT_CORRECT, violations.iterator().next().getMessage());
    }

    @Test
    void username_withNullValue_shouldFailValidation() {
        user.setUsername(null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(EMAIL_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // FirstName tests
    @Test
    void firstName_withValidName_shouldPassValidation() {
        user.setFirstName("John");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void firstName_withInvalidName_shouldFailValidation() {
        user.setFirstName("john");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertEquals(FIRST_NAME_FIRST_LETTER_UPPERCASE, violations.iterator().next().getMessage());
    }

    @Test
    void firstName_withShortName_shouldFailValidation() {
        user.setFirstName("J");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(FIRST_NAME_MIN_LENGTH)));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(FIRST_NAME_FIRST_LETTER_UPPERCASE)));
    }

    // LastName tests
    @Test
    void lastName_withValidName_shouldPassValidation() {
        user.setLastName("Smith");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void lastName_withInvalidName_shouldFailValidation() {
        user.setLastName("smith");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertEquals(LAST_NAME_FIRST_LETTER_UPPERCASE, violations.iterator().next().getMessage());
    }

    @Test
    void lastName_withShortName_shouldFailValidation() {
        user.setLastName("S");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(LAST_NAME_MIN_LENGTH)));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(LAST_NAME_FIRST_LETTER_UPPERCASE)));
    }

    // Password tests
    @Test
    void password_withValidLength_shouldPassValidation() {
        user.setPassword("StrongPass123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    @Test
    void password_withShortValue_shouldFailValidation() {
        user.setPassword("12");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
        assertEquals(PASSWORD_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    // Enabled tests
    @Test
    void enabled_getAndSet_shouldStoreAndRetrieveValue() {
        user.setEnabled(true);

        assertTrue(user.isEnabled());

        user.setEnabled(false);

        assertFalse(user.isEnabled());
    }

    // Authorities (Roles) tests
    @Test
    void authorities_withValidRoles_shouldProperlyStoreAndRetrieve() {
        Role role1 = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");

        Set<Role> roles = Set.of(role1, role2);
        user.setAuthorities(roles);

        assertEquals(roles, user.getAuthorities());
        assertTrue(user.getAuthorities().contains(role1));
        assertTrue(user.getAuthorities().contains(role2));
    }

    @Test
    void authorities_withNoRoles_shouldBeEmptyInitially() {
        user.setAuthorities(null);

        assertNull(user.getAuthorities());
    }

    @Test
    void accountNonExpired_defaultValue_shouldBeTrue() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void accountNonLocked_defaultValue_shouldBeTrue() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void credentialsNonExpired_defaultValue_shouldBeTrue() {
        assertTrue(user.isCredentialsNonExpired());
    }

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
