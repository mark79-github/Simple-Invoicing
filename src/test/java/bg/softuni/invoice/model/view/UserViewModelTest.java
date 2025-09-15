package bg.softuni.invoice.model.view;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserViewModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        UserViewModel userViewModel = new UserViewModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174008";
        userViewModel.setId(expectedId);

        String actualId = userViewModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        UserViewModel userViewModel = new UserViewModel();

        String actualId = userViewModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setId(null);

        String actualId = userViewModel.getId();

        assertNull(actualId);
    }

    // Username tests
    @Test
    void username_withValidValue_shouldReturnCorrectUsername() {
        UserViewModel userViewModel = new UserViewModel();
        String expectedUsername = "john.doe";
        userViewModel.setUsername(expectedUsername);

        String actualUsername = userViewModel.getUsername();

        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    void username_whenNotSet_shouldReturnNull() {
        UserViewModel userViewModel = new UserViewModel();

        String actualUsername = userViewModel.getUsername();

        assertNull(actualUsername);
    }

    @Test
    void username_withNullValue_shouldSetAndReturnNull() {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setUsername(null);

        String actualUsername = userViewModel.getUsername();

        assertNull(actualUsername);
    }

    // First name tests
    @Test
    void firstName_withValidValue_shouldReturnCorrectFirstName() {
        UserViewModel userViewModel = new UserViewModel();
        String expectedFirstName = "John";
        userViewModel.setFirstName(expectedFirstName);

        String actualFirstName = userViewModel.getFirstName();

        assertEquals(expectedFirstName, actualFirstName);
    }

    @Test
    void firstName_whenNotSet_shouldReturnNull() {
        UserViewModel userViewModel = new UserViewModel();

        String actualFirstName = userViewModel.getFirstName();

        assertNull(actualFirstName);
    }

    @Test
    void firstName_withNullValue_shouldSetAndReturnNull() {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setFirstName(null);

        String actualFirstName = userViewModel.getFirstName();

        assertNull(actualFirstName);
    }

    // Last name tests
    @Test
    void lastName_withValidValue_shouldReturnCorrectLastName() {
        UserViewModel userViewModel = new UserViewModel();
        String expectedLastName = "Doe";
        userViewModel.setLastName(expectedLastName);

        String actualLastName = userViewModel.getLastName();

        assertEquals(expectedLastName, actualLastName);
    }

    @Test
    void lastName_whenNotSet_shouldReturnNull() {
        UserViewModel userViewModel = new UserViewModel();

        String actualLastName = userViewModel.getLastName();

        assertNull(actualLastName);
    }

    @Test
    void lastName_withNullValue_shouldSetAndReturnNull() {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setLastName(null);

        String actualLastName = userViewModel.getLastName();

        assertNull(actualLastName);
    }

    // Authorities tests
    @Test
    void authorities_withValidValue_shouldReturnCorrectAuthorities() {
        UserViewModel userViewModel = new UserViewModel();
        Set<String> expectedAuthorities = new HashSet<>();
        expectedAuthorities.add("ROLE_USER");
        expectedAuthorities.add("ROLE_ADMIN");
        userViewModel.setAuthorities(expectedAuthorities);

        Set<String> actualAuthorities = userViewModel.getAuthorities();

        assertEquals(actualAuthorities.size(), expectedAuthorities.size());
        assertTrue(actualAuthorities.containsAll(expectedAuthorities));
    }

    @Test
    void authorities_whenNotSet_shouldReturnEmptyCollection() {
        UserViewModel userViewModel = new UserViewModel();

        Set<String> actualAuthorities = userViewModel.getAuthorities();

        assertNotNull(actualAuthorities);
        assertTrue(actualAuthorities.isEmpty());
    }

    @Test
    void authorities_withNullValue_shouldSetAndReturnNull() {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setAuthorities(null);

        Set<String> actualAuthorities = userViewModel.getAuthorities();

        assertNull(actualAuthorities);
    }

    // Enabled tests
    @Test
    void enabled_withValidValue_shouldReturnCorrectStatus() {
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setEnabled(true);

        boolean actualEnabled = userViewModel.isEnabled();

        assertTrue(actualEnabled);
    }

    @Test
    void enabled_whenNotSet_shouldReturnDefaultValue() {
        UserViewModel userViewModel = new UserViewModel();

        boolean actualEnabled = userViewModel.isEnabled();

        assertFalse(actualEnabled);
    }
}
