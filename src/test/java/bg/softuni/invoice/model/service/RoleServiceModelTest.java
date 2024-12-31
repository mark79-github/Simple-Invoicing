package bg.softuni.invoice.model.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RoleServiceModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        RoleServiceModel roleServiceModel = new RoleServiceModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174002"; // Example UUID
        roleServiceModel.setId(expectedId);

        String actualId = roleServiceModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        RoleServiceModel roleServiceModel = new RoleServiceModel();

        String actualId = roleServiceModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        RoleServiceModel roleServiceModel = new RoleServiceModel();
        roleServiceModel.setId(null);

        String actualId = roleServiceModel.getId();

        assertNull(actualId);
    }

    // Authority tests
    @Test
    void authority_withValidValue_shouldReturnCorrectAuthority() {
        RoleServiceModel roleServiceModel = new RoleServiceModel();
        String expectedAuthority = "ROLE_ADMIN";
        roleServiceModel.setAuthority(expectedAuthority);

        String actualAuthority = roleServiceModel.getAuthority();

        assertEquals(expectedAuthority, actualAuthority);
    }

    @Test
    void authority_whenNotSet_shouldReturnNull() {
        RoleServiceModel roleServiceModel = new RoleServiceModel();

        String actualAuthority = roleServiceModel.getAuthority();

        assertNull(actualAuthority);
    }

    @Test
    void authority_withNullValue_shouldSetAndReturnNull() {
        RoleServiceModel roleServiceModel = new RoleServiceModel();
        roleServiceModel.setAuthority(null);

        String actualAuthority = roleServiceModel.getAuthority();

        assertNull(actualAuthority);
    }
}
