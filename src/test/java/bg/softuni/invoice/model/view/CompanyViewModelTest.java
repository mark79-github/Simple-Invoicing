package bg.softuni.invoice.model.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class CompanyViewModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174003"; // Example UUID
        companyViewModel.setId(expectedId);

        String actualId = companyViewModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        CompanyViewModel companyViewModel = new CompanyViewModel();

        String actualId = companyViewModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        companyViewModel.setId(null);

        String actualId = companyViewModel.getId();

        assertNull(actualId);
    }

    // Name tests
    @Test
    void name_withValidValue_shouldReturnCorrectName() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        String expectedName = "Example Company";
        companyViewModel.setName(expectedName);

        String actualName = companyViewModel.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void name_whenNotSet_shouldReturnNull() {
        CompanyViewModel companyViewModel = new CompanyViewModel();

        String actualName = companyViewModel.getName();

        assertNull(actualName);
    }

    @Test
    void name_withNullValue_shouldSetAndReturnNull() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        companyViewModel.setName(null);

        String actualName = companyViewModel.getName();

        assertNull(actualName);
    }

    // Address tests
    @Test
    void address_withValidValue_shouldReturnCorrectAddress() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        String expectedAddress = "123 Main Street, Sample City";
        companyViewModel.setAddress(expectedAddress);

        String actualAddress = companyViewModel.getAddress();

        assertEquals(expectedAddress, actualAddress);
    }

    @Test
    void address_whenNotSet_shouldReturnNull() {
        CompanyViewModel companyViewModel = new CompanyViewModel();

        String actualAddress = companyViewModel.getAddress();

        assertNull(actualAddress);
    }

    @Test
    void address_withNullValue_shouldSetAndReturnNull() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        companyViewModel.setAddress(null);

        String actualAddress = companyViewModel.getAddress();

        assertNull(actualAddress);
    }

    // Unique identifier tests
    @Test
    void uniqueIdentifier_withValidValue_shouldReturnCorrectUniqueIdentifier() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        String expectedIdentifier = "BG123456789";
        companyViewModel.setUniqueIdentifier(expectedIdentifier);

        String actualIdentifier = companyViewModel.getUniqueIdentifier();

        assertEquals(expectedIdentifier, actualIdentifier);
    }

    @Test
    void uniqueIdentifier_whenNotSet_shouldReturnNull() {
        CompanyViewModel companyViewModel = new CompanyViewModel();

        String actualIdentifier = companyViewModel.getUniqueIdentifier();

        assertNull(actualIdentifier);
    }

    @Test
    void uniqueIdentifier_withNullValue_shouldSetAndReturnNull() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        companyViewModel.setUniqueIdentifier(null);

        String actualIdentifier = companyViewModel.getUniqueIdentifier();

        assertNull(actualIdentifier);
    }

    // Supplier tests
    @Test
    void supplier_withValidValue_shouldReturnCorrectSupplierStatus() {
        CompanyViewModel companyViewModel = new CompanyViewModel();
        boolean expectedSupplier = true;
        companyViewModel.setSupplier(expectedSupplier);

        boolean actualSupplier = companyViewModel.isSupplier();

        assertEquals(expectedSupplier, actualSupplier);
    }

    @Test
    void supplier_whenNotSet_shouldReturnDefaultValue() {
        CompanyViewModel companyViewModel = new CompanyViewModel();

        boolean actualSupplier = companyViewModel.isSupplier();

        assertFalse(actualSupplier);
    }
}

