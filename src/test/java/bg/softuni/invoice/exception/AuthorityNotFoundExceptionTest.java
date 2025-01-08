package bg.softuni.invoice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthorityNotFoundExceptionTest {

    @Test
    void testGetStatus_ShouldReturn404() {
        String errorMessage = "Test error message";
        AuthorityNotFoundException exception = new AuthorityNotFoundException(errorMessage);

        assertEquals(404, exception.getStatus());
    }

    @Test
    void testConstructor_ShouldStoreMessage() {
        String errorMessage = "Test Role Not Found";
        AuthorityNotFoundException exception = new AuthorityNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionThrown_ShouldContainCorrectStatusAndMessage() {
        String errorMessage = "Role not found";
        AuthorityNotFoundException exception = assertThrows(AuthorityNotFoundException.class, () -> {
            throw new AuthorityNotFoundException(errorMessage);
        });

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(404, exception.getStatus());
    }
}
