package bg.softuni.invoice.model.view;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemViewModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        ItemViewModel itemViewModel = new ItemViewModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174005";
        itemViewModel.setId(expectedId);

        String actualId = itemViewModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();

        String actualId = itemViewModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();
        itemViewModel.setId(null);

        String actualId = itemViewModel.getId();

        assertNull(actualId);
    }

    // Name tests
    @Test
    void name_withValidValue_shouldReturnCorrectName() {
        ItemViewModel itemViewModel = new ItemViewModel();
        String expectedName = "Example Item";
        itemViewModel.setName(expectedName);

        String actualName = itemViewModel.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void name_whenNotSet_shouldReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();

        String actualName = itemViewModel.getName();

        assertNull(actualName);
    }

    @Test
    void name_withNullValue_shouldSetAndReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();
        itemViewModel.setName(null);

        String actualName = itemViewModel.getName();

        assertNull(actualName);
    }

    // Price tests
    @Test
    void price_withValidValue_shouldReturnCorrectPrice() {
        ItemViewModel itemViewModel = new ItemViewModel();
        BigDecimal expectedPrice = new BigDecimal("49.99");
        itemViewModel.setPrice(expectedPrice);

        BigDecimal actualPrice = itemViewModel.getPrice();

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void price_whenNotSet_shouldReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();

        BigDecimal actualPrice = itemViewModel.getPrice();

        assertNull(actualPrice);
    }

    @Test
    void price_withNullValue_shouldSetAndReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();
        itemViewModel.setPrice(null);

        BigDecimal actualPrice = itemViewModel.getPrice();

        assertNull(actualPrice);
    }

    // VAT value tests
    @Test
    void vatValue_withValidValue_shouldReturnCorrectVatValue() {
        ItemViewModel itemViewModel = new ItemViewModel();
        String expectedVatValue = "20%";
        itemViewModel.setVatValue(expectedVatValue);

        String actualVatValue = itemViewModel.getVatValue();

        assertEquals(expectedVatValue, actualVatValue);
    }

    @Test
    void vatValue_whenNotSet_shouldReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();

        String actualVatValue = itemViewModel.getVatValue();

        assertNull(actualVatValue);
    }

    @Test
    void vatValue_withNullValue_shouldSetAndReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();
        itemViewModel.setVatValue(null);

        String actualVatValue = itemViewModel.getVatValue();

        assertNull(actualVatValue);
    }

    // Image URL tests
    @Test
    void imageUrl_withValidValue_shouldReturnCorrectImageUrl() {
        ItemViewModel itemViewModel = new ItemViewModel();
        String expectedImageUrl = "https://example.com/image.jpg";
        itemViewModel.setImageUrl(expectedImageUrl);

        String actualImageUrl = itemViewModel.getImageUrl();

        assertEquals(expectedImageUrl, actualImageUrl);
    }

    @Test
    void imageUrl_whenNotSet_shouldReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();

        String actualImageUrl = itemViewModel.getImageUrl();

        assertNull(actualImageUrl);
    }

    @Test
    void imageUrl_withNullValue_shouldSetAndReturnNull() {
        ItemViewModel itemViewModel = new ItemViewModel();
        itemViewModel.setImageUrl(null);

        String actualImageUrl = itemViewModel.getImageUrl();

        assertNull(actualImageUrl);
    }

    // Quantity tests
    @Test
    void quantity_withValidValue_shouldReturnCorrectQuantity() {
        ItemViewModel itemViewModel = new ItemViewModel();
        int expectedQuantity = 5;
        itemViewModel.setQuantity(expectedQuantity);

        int actualQuantity = itemViewModel.getQuantity();

        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void quantity_whenNotSet_shouldReturnDefaultValue() {
        ItemViewModel itemViewModel = new ItemViewModel();

        int actualQuantity = itemViewModel.getQuantity();

        assertEquals(0, actualQuantity);
    }
}
