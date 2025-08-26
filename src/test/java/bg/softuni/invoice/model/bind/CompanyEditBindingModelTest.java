package bg.softuni.invoice.model.bind;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.UNIQUE_IDENTIFIER_LENGTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompanyEditBindingModelTest {

    private static final String VALID_ID = "12345";
    private static final String VALID_NAME = "Valid Company Name";
    private static final String EMPTY_NAME = "";
    private static final String VALID_ADDRESS = "Valid Address";
    private static final String EMPTY_ADDRESS = "";
    private static final String VALID_IDENTIFIER = "123456789";

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = createValidator();
    }

    @Test
    void setCompany_withValidName_shouldPassValidation() {
        CompanyEditBindingModel model = new CompanyEditBindingModel();
        model.setName(VALID_NAME);

        Set<ConstraintViolation<CompanyEditBindingModel>> violations = validateCompanyModel(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_NAME, model.getName());
    }

    @Test
    void setCompany_withEmptyName_shouldFailValidation() {
        CompanyEditBindingModel model = new CompanyEditBindingModel();
        model.setName(EMPTY_NAME);

        Set<ConstraintViolation<CompanyEditBindingModel>> violations = validateCompanyModel(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void setCompany_withValidAddress_shouldPassValidation() {
        CompanyEditBindingModel model = new CompanyEditBindingModel();
        model.setAddress(VALID_ADDRESS);

        Set<ConstraintViolation<CompanyEditBindingModel>> violations = validateCompanyModel(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_ADDRESS, model.getAddress());
    }

    @Test
    void setCompany_withEmptyAddress_shouldFailValidation() {
        CompanyEditBindingModel model = new CompanyEditBindingModel();
        model.setName(EMPTY_ADDRESS);

        Set<ConstraintViolation<CompanyEditBindingModel>> violations = validateCompanyModel(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(NAME_MIN_LENGTH, violations.iterator().next().getMessage());
    }

    @Test
    void setCompany_withValidUniqueIdentifier_shouldPassValidation() {
        CompanyEditBindingModel model = new CompanyEditBindingModel();
        model.setUniqueIdentifier(VALID_IDENTIFIER);

        Set<ConstraintViolation<CompanyEditBindingModel>> violations = validateCompanyModel(model);

        assertTrue(violations.isEmpty());
        assertEquals(VALID_IDENTIFIER, model.getUniqueIdentifier());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "abcdefghi", "12345678a", "12345678", "1234567890", "123456789012", "12345678901234"})
    void setCompany_withInvalidUniqueIdentifier_shouldFailValidation(String uniqueIdentifier) {
        CompanyEditBindingModel model = new CompanyEditBindingModel();
        model.setUniqueIdentifier(uniqueIdentifier);

        Set<ConstraintViolation<CompanyEditBindingModel>> violations = validateCompanyModel(model);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals(UNIQUE_IDENTIFIER_LENGTH, violations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testCompany_withValidSupplier_shouldPassValidation(boolean supplier) {
        CompanyEditBindingModel model = new CompanyEditBindingModel();
        model.setSupplier(supplier);

        Set<ConstraintViolation<CompanyEditBindingModel>> violations = validator.validate(model);

        assertTrue(violations.isEmpty());
        assertEquals(supplier, model.isSupplier());
    }

    @Test
    void setCompany_withValidId_shouldSetIdCorrectly() {
        CompanyEditBindingModel model = new CompanyEditBindingModel();
        model.setId(VALID_ID);

        assertEquals(VALID_ID, model.getId());
    }

    private Set<ConstraintViolation<CompanyEditBindingModel>> validateCompanyModel(CompanyEditBindingModel model) {
        return validator.validate(model);
    }

    private Validator createValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }
}
