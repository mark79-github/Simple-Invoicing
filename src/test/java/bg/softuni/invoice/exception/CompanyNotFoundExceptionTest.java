package bg.softuni.invoice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompanyNotFoundExceptionTest {

    private static final String EXCEPTION_ERROR_MESSAGE = "Company not found";
    private static final int EXPECTED_STATUS = 404;

    @Test
    void shouldStoreMessageAndStatus_whenExceptionIsCreated() {
        CompanyNotFoundException exception = new CompanyNotFoundException(EXCEPTION_ERROR_MESSAGE);

        assertEquals(EXCEPTION_ERROR_MESSAGE, exception.getMessage());
        assertEquals(EXPECTED_STATUS, exception.getStatus());
    }

    @Test
    void shouldContainCorrectMessageAndStatus_whenExceptionIsThrown() {
        CompanyNotFoundException exception = assertThrows(CompanyNotFoundException.class, () -> {
            throw new CompanyNotFoundException(EXCEPTION_ERROR_MESSAGE);
        });

        assertEquals(EXCEPTION_ERROR_MESSAGE, exception.getMessage());
        assertEquals(EXPECTED_STATUS, exception.getStatus());
    }
}
