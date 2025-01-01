package bg.softuni.invoice.model.view;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SaleViewModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        SaleViewModel saleViewModel = new SaleViewModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174007";
        saleViewModel.setId(expectedId);

        String actualId = saleViewModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        SaleViewModel saleViewModel = new SaleViewModel();

        String actualId = saleViewModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        SaleViewModel saleViewModel = new SaleViewModel();
        saleViewModel.setId(null);

        String actualId = saleViewModel.getId();

        assertNull(actualId);
    }

    // Name tests
    @Test
    void name_withValidValue_shouldReturnCorrectName() {
        SaleViewModel saleViewModel = new SaleViewModel();
        String expectedName = "Example Sale";
        saleViewModel.setName(expectedName);

        String actualName = saleViewModel.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void name_whenNotSet_shouldReturnNull() {
        SaleViewModel saleViewModel = new SaleViewModel();

        String actualName = saleViewModel.getName();

        assertNull(actualName);
    }

    @Test
    void name_withNullValue_shouldSetAndReturnNull() {
        SaleViewModel saleViewModel = new SaleViewModel();
        saleViewModel.setName(null);

        String actualName = saleViewModel.getName();

        assertNull(actualName);
    }

    // Quantity tests
    @Test
    void quantity_withValidValue_shouldReturnCorrectQuantity() {
        SaleViewModel saleViewModel = new SaleViewModel();
        int expectedQuantity = 10;
        saleViewModel.setQuantity(expectedQuantity);

        int actualQuantity = saleViewModel.getQuantity();

        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void quantity_whenNotSet_shouldReturnDefaultValue() {
        SaleViewModel saleViewModel = new SaleViewModel();

        int actualQuantity = saleViewModel.getQuantity();

        assertEquals(0, actualQuantity);
    }

    // Price tests
    @Test
    void price_withValidValue_shouldReturnCorrectPrice() {
        SaleViewModel saleViewModel = new SaleViewModel();
        BigDecimal expectedPrice = new BigDecimal("899.99");
        saleViewModel.setPrice(expectedPrice);

        BigDecimal actualPrice = saleViewModel.getPrice();

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void price_whenNotSet_shouldReturnNull() {
        SaleViewModel saleViewModel = new SaleViewModel();

        BigDecimal actualPrice = saleViewModel.getPrice();

        assertNull(actualPrice);
    }

    @Test
    void price_withNullValue_shouldSetAndReturnNull() {
        SaleViewModel saleViewModel = new SaleViewModel();
        saleViewModel.setPrice(null);

        BigDecimal actualPrice = saleViewModel.getPrice();

        assertNull(actualPrice);
    }

    // VAT value tests
    @Test
    void vatValue_withValidValue_shouldReturnCorrectVatValue() {
        SaleViewModel saleViewModel = new SaleViewModel();
        int expectedVatValue = 20; // Example VAT Value (20%)
        saleViewModel.setVatValue(expectedVatValue);

        int actualVatValue = saleViewModel.getVatValue();

        assertEquals(expectedVatValue, actualVatValue);
    }

    @Test
    void vatValue_whenNotSet_shouldReturnDefaultValue() {
        SaleViewModel saleViewModel = new SaleViewModel();

        int actualVatValue = saleViewModel.getVatValue();

        assertEquals(0, actualVatValue);
    }
}
