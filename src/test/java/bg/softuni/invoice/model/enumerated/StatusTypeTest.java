package bg.softuni.invoice.model.enumerated;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusTypeTest {

    @ParameterizedTest
    @EnumSource(StatusType.class)
    void testGetTypeForAllStatusTypes(StatusType statusType) {
        String expectedType = switch (statusType) {
            case AWAIT -> "Await";
            case COMPLETE -> "Complete";
        };
        assertEquals(expectedType, statusType.getType());
    }

    @Test
    void testValueOfValidEnum() {
        StatusType statusType = StatusType.valueOf("AWAIT");
        assertEquals(StatusType.AWAIT, statusType);
    }

    @Test
    void testValueOfInvalidEnum() {
        assertThrows(IllegalArgumentException.class, () -> StatusType.valueOf("complete"));
    }
}
