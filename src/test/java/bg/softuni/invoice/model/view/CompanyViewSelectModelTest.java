package bg.softuni.invoice.model.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CompanyViewSelectModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        CompanyViewSelectModel companyViewSelectModel = new CompanyViewSelectModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174004";
        companyViewSelectModel.setId(expectedId);

        String actualId = companyViewSelectModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        CompanyViewSelectModel companyViewSelectModel = new CompanyViewSelectModel();

        String actualId = companyViewSelectModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        CompanyViewSelectModel companyViewSelectModel = new CompanyViewSelectModel();
        companyViewSelectModel.setId(null);

        String actualId = companyViewSelectModel.getId();

        assertNull(actualId);
    }

    // Name tests
    @Test
    void name_withValidValue_shouldReturnCorrectName() {
        CompanyViewSelectModel companyViewSelectModel = new CompanyViewSelectModel();
        String expectedName = "Example Select Company";
        companyViewSelectModel.setName(expectedName);

        String actualName = companyViewSelectModel.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void name_whenNotSet_shouldReturnNull() {
        CompanyViewSelectModel companyViewSelectModel = new CompanyViewSelectModel();

        String actualName = companyViewSelectModel.getName();

        assertNull(actualName);
    }

    @Test
    void name_withNullValue_shouldSetAndReturnNull() {
        CompanyViewSelectModel companyViewSelectModel = new CompanyViewSelectModel();
        companyViewSelectModel.setName(null);

        String actualName = companyViewSelectModel.getName();

        assertNull(actualName);
    }
}
