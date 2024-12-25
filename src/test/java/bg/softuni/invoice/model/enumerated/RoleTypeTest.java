package bg.softuni.invoice.model.enumerated;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RoleTypeTest {

    @ParameterizedTest
    @EnumSource(RoleType.class)
    void testGetTypeForAllRoleTypes(RoleType roleType) {
        String expectedType = switch (roleType) {
            case ADMIN -> "ROLE_ADMIN";
            case USER -> "ROLE_USER";
            case ROOT -> "ROLE_ROOT";
        };
        assertEquals(expectedType, roleType.getType());
    }

    @Test
    void testValueOfValidEnum() {
        RoleType roleType = RoleType.valueOf("USER");
        assertEquals(RoleType.USER, roleType);
    }

    @Test
    void testValueOfInvalidEnum() {
        assertThrows(IllegalArgumentException.class, () -> RoleType.valueOf("ROLE_USER"));
    }
}
