package bg.softuni.invoice.model.view;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LogViewModelTest {

    // ID tests
    @Test
    void id_withValidValue_shouldReturnCorrectId() {
        LogViewModel logViewModel = new LogViewModel();
        String expectedId = "123e4567-e89b-12d3-a456-426614174006";
        logViewModel.setId(expectedId);

        String actualId = logViewModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void id_whenNotSet_shouldReturnNull() {
        LogViewModel logViewModel = new LogViewModel();

        String actualId = logViewModel.getId();

        assertNull(actualId);
    }

    @Test
    void id_withNullValue_shouldSetAndReturnNull() {
        LogViewModel logViewModel = new LogViewModel();
        logViewModel.setId(null);

        String actualId = logViewModel.getId();

        assertNull(actualId);
    }

    // Request URI tests
    @Test
    void requestURI_withValidValue_shouldReturnCorrectRequestURI() {
        LogViewModel logViewModel = new LogViewModel();
        String expectedURI = "/api/v1/test";
        logViewModel.setRequestURI(expectedURI);

        String actualURI = logViewModel.getRequestURI();

        assertEquals(expectedURI, actualURI);
    }

    @Test
    void requestURI_whenNotSet_shouldReturnNull() {
        LogViewModel logViewModel = new LogViewModel();

        String actualURI = logViewModel.getRequestURI();

        assertNull(actualURI);
    }

    @Test
    void requestURI_withNullValue_shouldSetAndReturnNull() {
        LogViewModel logViewModel = new LogViewModel();
        logViewModel.setRequestURI(null);

        String actualURI = logViewModel.getRequestURI();

        assertNull(actualURI);
    }

    // Method tests
    @Test
    void method_withValidValue_shouldReturnCorrectMethod() {
        LogViewModel logViewModel = new LogViewModel();
        String expectedMethod = "POST";
        logViewModel.setMethod(expectedMethod);

        String actualMethod = logViewModel.getMethod();

        assertEquals(expectedMethod, actualMethod);
    }

    @Test
    void method_whenNotSet_shouldReturnNull() {
        LogViewModel logViewModel = new LogViewModel();

        String actualMethod = logViewModel.getMethod();

        assertNull(actualMethod);
    }

    @Test
    void method_withNullValue_shouldSetAndReturnNull() {
        LogViewModel logViewModel = new LogViewModel();
        logViewModel.setMethod(null);

        String actualMethod = logViewModel.getMethod();

        assertNull(actualMethod);
    }

    // DateTime tests
    @Test
    void dateTime_withValidValue_shouldReturnCorrectDateTime() {
        LogViewModel logViewModel = new LogViewModel();
        LocalDateTime expectedDateTime = LocalDateTime.of(2023, 12, 25, 15, 30, 45);
        logViewModel.setDateTime(expectedDateTime);

        LocalDateTime actualDateTime = logViewModel.getDateTime();

        assertEquals(expectedDateTime, actualDateTime);
    }

    @Test
    void dateTime_whenNotSet_shouldReturnNull() {
        LogViewModel logViewModel = new LogViewModel();

        LocalDateTime actualDateTime = logViewModel.getDateTime();

        assertNull(actualDateTime);
    }

    @Test
    void dateTime_withNullValue_shouldSetAndReturnNull() {
        LogViewModel logViewModel = new LogViewModel();
        logViewModel.setDateTime(null);

        LocalDateTime actualDateTime = logViewModel.getDateTime();

        assertNull(actualDateTime);
    }

    // User tests
    @Test
    void user_withValidValue_shouldReturnCorrectUser() {
        LogViewModel logViewModel = new LogViewModel();
        String expectedUser = "TestUser";
        logViewModel.setUser(expectedUser);

        String actualUser = logViewModel.getUser();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void user_whenNotSet_shouldReturnNull() {
        LogViewModel logViewModel = new LogViewModel();

        String actualUser = logViewModel.getUser();

        assertNull(actualUser);
    }

    @Test
    void user_withNullValue_shouldSetAndReturnNull() {
        LogViewModel logViewModel = new LogViewModel();
        logViewModel.setUser(null);

        String actualUser = logViewModel.getUser();

        assertNull(actualUser);
    }
}
