package bg.softuni.invoice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void index_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(view().name("home/index"));
    }

    @Test
    @WithMockUser
    void index_withAuthenticatedUserShouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(view().name("home/home"));
    }

    @Test
    @WithMockUser()
    void home_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/home").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/invoice/all"));
    }

    @Test
    void index_withNullPrincipal_shouldReturnIndexView() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(view().name("home/index"));
    }

    @Test
    @WithMockUser
    void index_withAuthenticatedPrincipal_shouldReturnHomeView() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(view().name("home/home"));
    }
}
