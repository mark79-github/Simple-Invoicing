package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.enumerated.VatValue;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SaleServiceModelTest {

    // Name tests
    @Test
    void name_withValidValue_shouldReturnCorrectName() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();
        String expectedName = "Sample Sale";
        saleServiceModel.setName(expectedName);

        String actualName = saleServiceModel.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void name_whenNotSet_shouldReturnNull() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();

        String actualName = saleServiceModel.getName();

        assertNull(actualName);
    }

    @Test
    void name_withNullValue_shouldSetAndReturnNull() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();
        saleServiceModel.setName(null);

        String actualName = saleServiceModel.getName();

        assertNull(actualName);
    }

    // Quantity tests
    @Test
    void quantity_withValidValue_shouldReturnCorrectQuantity() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();
        int expectedQuantity = 10;
        saleServiceModel.setQuantity(expectedQuantity);

        int actualQuantity = saleServiceModel.getQuantity();

        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void quantity_whenNotSet_shouldReturnDefaultValue() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();

        int actualQuantity = saleServiceModel.getQuantity();

        assertEquals(0, actualQuantity);
    }

    // Price tests
    @Test
    void price_withValidValue_shouldReturnCorrectPrice() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();
        BigDecimal expectedPrice = new BigDecimal("49.99");
        saleServiceModel.setPrice(expectedPrice);

        BigDecimal actualPrice = saleServiceModel.getPrice();

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void price_whenNotSet_shouldReturnNull() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();

        BigDecimal actualPrice = saleServiceModel.getPrice();

        assertNull(actualPrice);
    }

    @Test
    void price_withNullValue_shouldSetAndReturnNull() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();
        saleServiceModel.setPrice(null);

        BigDecimal actualPrice = saleServiceModel.getPrice();

        assertNull(actualPrice);
    }

    // VAT Value tests
    @Test
    void vatValue_withValidValue_shouldReturnCorrectVatValue() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();
        VatValue expectedVatValue = VatValue.TWENTY;
        saleServiceModel.setVatValue(expectedVatValue);

        VatValue actualVatValue = saleServiceModel.getVatValue();

        assertEquals(expectedVatValue, actualVatValue);
    }

    @Test
    void vatValue_whenNotSet_shouldReturnNull() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();

        VatValue actualVatValue = saleServiceModel.getVatValue();

        assertNull(actualVatValue);
    }

    @Test
    void vatValue_withNullValue_shouldSetAndReturnNull() {
        SaleServiceModel saleServiceModel = new SaleServiceModel();
        saleServiceModel.setVatValue(null);

        VatValue actualVatValue = saleServiceModel.getVatValue();

        assertNull(actualVatValue);
    }
}
