package bg.softuni.invoice.model.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.ADDRESS_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.ADDRESS_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.UNIQUE_IDENTIFIER_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.UNIQUE_IDENTIFIER_NOT_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompanyTest {

    public static final String VALID_NAME = "Valid Company Name";
    public static final String VALID_ADDRESS = "123 Valid Street";
    public static final String VALID_UNIQUE_IDENTIFIER = "123456789";

    private Validator validator;
    private Company company;

    @BeforeEach
    void setUp() {
        validator = createValidator();
        company = new Company();
        company.setName(VALID_NAME);
        company.setAddress(VALID_ADDRESS);
        company.setUniqueIdentifier(VALID_UNIQUE_IDENTIFIER);
        company.setSupplier(true);
    }

    // Name tests
    @Test
    void name_withValidValue_shouldPassValidation() {
        company.setName(VALID_NAME);

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_NAME, company.getName());
    }

    @Test
    void name_withShortValue_shouldFailValidation() {
        company.setName("Co");

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void name_withNullValue_shouldFailValidation() {
        company.setName(null);

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // Address tests
    @Test
    void address_withValidValue_shouldPassValidation() {
        company.setAddress(VALID_ADDRESS);

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_ADDRESS, company.getAddress());
    }

    @Test
    void address_withShortValue_shouldFailValidation() {
        company.setAddress("12");

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(ADDRESS_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void address_withNullValue_shouldFailValidation() {
        company.setAddress(null);

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(ADDRESS_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // Unique Identifier tests
    @Test
    void uniqueIdentifier_withValidValue_shouldPassValidation() {
        company.setUniqueIdentifier(VALID_UNIQUE_IDENTIFIER);

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_UNIQUE_IDENTIFIER, company.getUniqueIdentifier());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "1234567890ABCDE", "abcdef"})
    void uniqueIdentifier_withInvalidValue_shouldFailValidation(String value) {
        company.setUniqueIdentifier(value);

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(UNIQUE_IDENTIFIER_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void uniqueIdentifier_withNullValue_shouldFailValidation() {
        company.setUniqueIdentifier(null);

        Set<ConstraintViolation<Company>> violations = validator.validate(company);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(UNIQUE_IDENTIFIER_NOT_EMPTY, violations.iterator().next().getMessage());
    }

    // Supplier tests
    @Test
    void supplier_withTrueValue_shouldPassValidation() {
        company.setSupplier(true);

        assertTrue(company.isSupplier());
    }

    @Test
    void supplier_withFalseValue_shouldPassValidation() {
        company.setSupplier(false);

        assertFalse(company.isSupplier());
    }

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
