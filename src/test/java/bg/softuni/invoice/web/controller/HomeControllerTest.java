package bg.softuni.invoice.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));
    }

    @Test
    void index_withAuthenticatedUserShouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/").with(user("test-user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"));
    }

    @Test
    void home_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/home").with(user("test-user").roles("USER")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/invoice/all"));
    }

    @Test
    void index_withNullPrincipal_shouldReturnIndexView() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));
    }

    @Test
    void index_withAuthenticatedPrincipal_shouldReturnHomeView() throws Exception {
        this.mockMvc
                .perform(get("/").with(user("test-user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"));
    }
}
