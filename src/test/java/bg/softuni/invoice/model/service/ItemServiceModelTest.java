package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.enumerated.VatValue;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItemServiceModelTest {

    // Name tests
    @Test
    void name_withValidValue_shouldReturnCorrectName() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        String expectedName = "Sample Item";
        itemServiceModel.setName(expectedName);

        String actualName = itemServiceModel.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void name_whenNotSet_shouldReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();

        String actualName = itemServiceModel.getName();

        assertNull(actualName);
    }

    @Test
    void name_withEmptyString_shouldReturnEmptyString() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        String expectedName = "";
        itemServiceModel.setName(expectedName);

        String actualName = itemServiceModel.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void name_withNullValue_shouldSetAndReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        itemServiceModel.setName(null);

        String actualName = itemServiceModel.getName();

        assertNull(actualName);
    }

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174000"; // Example UUID
        itemServiceModel.setId(expectedId);

        String actualId = itemServiceModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();

        String actualId = itemServiceModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withEmptyString_shouldReturnEmptyString() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        String expectedId = "";
        itemServiceModel.setId(expectedId);

        String actualId = itemServiceModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        itemServiceModel.setId(null);

        String actualId = itemServiceModel.getId();

        assertNull(actualId);
    }

    // Price tests
    @Test
    void price_withValidValue_shouldReturnCorrectPrice() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        BigDecimal expectedPrice = new BigDecimal("19.99");
        itemServiceModel.setPrice(expectedPrice);

        BigDecimal actualPrice = itemServiceModel.getPrice();

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void price_whenNotSet_shouldReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();

        BigDecimal actualPrice = itemServiceModel.getPrice();

        assertNull(actualPrice);
    }

    @Test
    void price_withNullValue_shouldSetAndReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        itemServiceModel.setPrice(null);

        BigDecimal actualPrice = itemServiceModel.getPrice();

        assertNull(actualPrice);
    }

    // VAT value tests
    @Test
    void vatValue_withValidValue_shouldReturnCorrectVatValue() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        VatValue expectedVatValue = VatValue.TWENTY;
        itemServiceModel.setVatValue(expectedVatValue);

        VatValue actualVatValue = itemServiceModel.getVatValue();

        assertEquals(expectedVatValue, actualVatValue);
    }

    @Test
    void vatValue_whenNotSet_shouldReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();

        VatValue actualVatValue = itemServiceModel.getVatValue();

        assertNull(actualVatValue);
    }

    @Test
    void vatValue_withNullValue_shouldSetAndReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        itemServiceModel.setVatValue(null);

        VatValue actualVatValue = itemServiceModel.getVatValue();

        assertNull(actualVatValue);
    }

    // Quantity tests
    @Test
    void quantity_withValidValue_shouldReturnCorrectQuantity() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        int expectedQuantity = 42;
        itemServiceModel.setQuantity(expectedQuantity);

        int actualQuantity = itemServiceModel.getQuantity();

        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void quantity_whenNotSet_shouldReturnDefaultValue() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();

        int actualQuantity = itemServiceModel.getQuantity();

        assertEquals(0, actualQuantity);
    }

    // Image URL tests
    @Test
    void imageUrl_withValidValue_shouldReturnCorrectUrl() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        String expectedUrl = "https://example.com/image.jpg";
        itemServiceModel.setImageUrl(expectedUrl);

        String actualUrl = itemServiceModel.getImageUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void imageUrl_whenNotSet_shouldReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();

        String actualUrl = itemServiceModel.getImageUrl();

        assertNull(actualUrl);
    }

    @Test
    void imageUrl_withEmptyString_shouldReturnEmptyString() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        String expectedUrl = "";
        itemServiceModel.setImageUrl(expectedUrl);

        String actualUrl = itemServiceModel.getImageUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void imageUrl_withNullValue_shouldSetAndReturnNull() {
        ItemServiceModel itemServiceModel = new ItemServiceModel();
        itemServiceModel.setImageUrl(null);

        String actualUrl = itemServiceModel.getImageUrl();

        assertNull(actualUrl);
    }
}
