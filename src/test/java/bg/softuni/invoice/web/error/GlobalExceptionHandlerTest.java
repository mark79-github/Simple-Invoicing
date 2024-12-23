package bg.softuni.invoice.web.error;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    private static final String EXPECTED_VIEW_NAME = "error";
    private static final String TEST_EXCEPTION_MESSAGE = "Test exception message";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void handleAllErrors_whenFailureUrl_redirectsToLoginPage() throws Exception {
        mockMvc.perform(get("/a-nonexistent-url"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/user/login"));
    }

    @Test
    void handleAllErrors_whenGeneralExceptionIsThrown_addsMessageToModelAndReturnsErrorView() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Model model = new ExtendedModelMap();
        Exception exception = new Exception(TEST_EXCEPTION_MESSAGE);

        String viewName = globalExceptionHandler.handleAllErrors(model, exception);

        assertEquals(TEST_EXCEPTION_MESSAGE, model.asMap().get("message"));
        assertEquals(EXPECTED_VIEW_NAME, viewName);
    }

    @Test
    void handleAllErrors_whenExceptionMessageIsNull_addsNullMessageToModel() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Model model = new ExtendedModelMap();
        Exception exception = new Exception((Throwable) null);

        String viewName = globalExceptionHandler.handleAllErrors(model, exception);

        assertNull(model.asMap().get("message"));
        assertEquals(EXPECTED_VIEW_NAME, viewName);
    }

    @Test
    void handleAllErrors_whenAttributeAlreadyExists_overwritesExistingValue() {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Model model = new ExtendedModelMap();
        model.addAttribute("message", "Old message");
        Exception exception = new Exception("New message");

        String viewName = globalExceptionHandler.handleAllErrors(model, exception);

        assertEquals("New message", model.asMap().get("message"));
        assertEquals(EXPECTED_VIEW_NAME, viewName);
    }
}
