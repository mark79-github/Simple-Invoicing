package bg.softuni.invoice.model.enumerated;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VatValueTest {

    @ParameterizedTest
    @EnumSource(VatValue.class)
    void testGetTypeForAllVatValues(VatValue vatValue) {
        int expectedType = switch (vatValue) {
            case ZERO -> 0;
            case NINE -> 9;
            case TWENTY -> 20;
        };
        assertEquals(expectedType, vatValue.getValue());
    }

    @Test
    void testValueOfValidEnum() {
        VatValue vatValue = VatValue.valueOf("ZERO");
        assertEquals(VatValue.ZERO, vatValue);
    }

    @Test
    void testValueOfInvalidEnum() {
        assertThrows(IllegalArgumentException.class, () -> VatValue.valueOf("twenty"));
    }
}
