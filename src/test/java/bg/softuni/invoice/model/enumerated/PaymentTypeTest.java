package bg.softuni.invoice.model.enumerated;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentTypeTest {

    @ParameterizedTest
    @EnumSource(PaymentType.class)
    void testGetTypeForAllPaymentTypes(PaymentType paymentType) {
        String expectedType = switch (paymentType) {
            case CASH -> "Cash";
            case TRANSFER -> "Transfer";
        };
        assertEquals(expectedType, paymentType.getType());
    }

    @Test
    void testValueOfValidEnum() {
        PaymentType paymentType = PaymentType.valueOf("CASH");
        assertEquals(PaymentType.CASH, paymentType);
    }

    @Test
    void testValueOfInvalidEnum() {
        assertThrows(IllegalArgumentException.class, () -> PaymentType.valueOf("cash"));
    }
}
