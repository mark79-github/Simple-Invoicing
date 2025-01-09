package bg.softuni.invoice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserNotFoundExceptionTest {

    private static final String EXCEPTION_ERROR_MESSAGE = "User not found";
    private static final int EXPECTED_STATUS = 404;

    @Test
    void shouldStoreMessageAndStatus_whenExceptionIsCreated() {
        UserNotFoundException exception = new UserNotFoundException(EXCEPTION_ERROR_MESSAGE);

        assertEquals(EXCEPTION_ERROR_MESSAGE, exception.getMessage());
        assertEquals(EXPECTED_STATUS, exception.getStatus());
    }

    @Test
    void shouldContainCorrectMessageAndStatus_whenExceptionIsThrown() {
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            throw new UserNotFoundException(EXCEPTION_ERROR_MESSAGE);
        });

        assertEquals(EXCEPTION_ERROR_MESSAGE, exception.getMessage());
        assertEquals(EXPECTED_STATUS, exception.getStatus());
    }
}
