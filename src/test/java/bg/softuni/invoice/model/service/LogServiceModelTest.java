package bg.softuni.invoice.model.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LogServiceModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        LogServiceModel logServiceModel = new LogServiceModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174001";
        logServiceModel.setId(expectedId);

        String actualId = logServiceModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();

        String actualId = logServiceModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setId(null);

        String actualId = logServiceModel.getId();

        assertNull(actualId);
    }

    // Request URI tests
    @Test
    void requestURI_withValidValue_shouldReturnCorrectRequestURI() {
        LogServiceModel logServiceModel = new LogServiceModel();
        String expectedURI = "/api/items";
        logServiceModel.setRequestURI(expectedURI);

        String actualURI = logServiceModel.getRequestURI();

        assertEquals(expectedURI, actualURI);
    }

    @Test
    void requestURI_whenNotSet_shouldReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();

        String actualURI = logServiceModel.getRequestURI();

        assertNull(actualURI);
    }

    @Test
    void requestURI_withNullValue_shouldSetAndReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setRequestURI(null);

        String actualURI = logServiceModel.getRequestURI();

        assertNull(actualURI);
    }

    // Method tests
    @Test
    void method_withValidValue_shouldReturnCorrectMethod() {
        LogServiceModel logServiceModel = new LogServiceModel();
        String expectedMethod = "POST";
        logServiceModel.setMethod(expectedMethod);

        String actualMethod = logServiceModel.getMethod();

        assertEquals(expectedMethod, actualMethod);
    }

    @Test
    void method_whenNotSet_shouldReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();

        String actualMethod = logServiceModel.getMethod();

        assertNull(actualMethod);
    }

    @Test
    void method_withNullValue_shouldSetAndReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setMethod(null);

        String actualMethod = logServiceModel.getMethod();

        assertNull(actualMethod);
    }

    // DateTime tests
    @Test
    void dateTime_withValidValue_shouldReturnCorrectDateTime() {
        LogServiceModel logServiceModel = new LogServiceModel();
        LocalDateTime expectedDateTime = LocalDateTime.of(2023, 10, 10, 10, 10);
        logServiceModel.setDateTime(expectedDateTime);

        LocalDateTime actualDateTime = logServiceModel.getDateTime();

        assertEquals(expectedDateTime, actualDateTime);
    }

    @Test
    void dateTime_whenNotSet_shouldReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();

        LocalDateTime actualDateTime = logServiceModel.getDateTime();

        assertNull(actualDateTime);
    }

    @Test
    void dateTime_withNullValue_shouldSetAndReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setDateTime(null);

        LocalDateTime actualDateTime = logServiceModel.getDateTime();

        assertNull(actualDateTime);
    }

    // User tests
    @Test
    void user_withValidValue_shouldReturnCorrectUser() {
        LogServiceModel logServiceModel = new LogServiceModel();
        UserServiceModel expectedUser = new UserServiceModel();
        expectedUser.setId("123");
        expectedUser.setUsername("testuser");

        logServiceModel.setUser(expectedUser);

        UserServiceModel actualUser = logServiceModel.getUser();

        assertEquals(expectedUser, actualUser);
        assertEquals("123", actualUser.getId());
        assertEquals("testuser", actualUser.getUsername());
    }

    @Test
    void user_whenNotSet_shouldReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();

        UserServiceModel actualUser = logServiceModel.getUser();

        assertNull(actualUser);
    }

    @Test
    void user_withNullValue_shouldSetAndReturnNull() {
        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUser(null);

        UserServiceModel actualUser = logServiceModel.getUser();

        assertNull(actualUser);
    }
}
