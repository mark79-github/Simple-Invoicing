package bg.softuni.invoice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void login_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/user/login"))
                .andExpect(view().name("user/login"));
    }

    @Test
    @WithMockUser
    public void login_withLoggedInUserShouldReturnErrorView() throws Exception {
        this.mockMvc
                .perform(get("/user/login"))
                .andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/error"));
    }

    @Test
    public void register_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/user/register"))
                .andExpect(model().attributeExists("userRegisterBindingModel"))
                .andExpect(view().name("user/register"));
    }

    @Test
    public void register_registerSuccessfullyRedirect() throws Exception {
        this.mockMvc
                .perform(post("/user/register").with(csrf())
                        .param("username", "admin@admin.com")
                        .param("firstName", "Admin")
                        .param("lastName", "Admin")
                        .param("password", "admin")
                        .param("confirmPassword", "admin")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:login"));
    }

    @Test
    public void register_whenBindingResultHasErrorsRedirect() throws Exception {
        this.mockMvc
                .perform(post("/user/register").with(csrf())
                        .param("username", "")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("password", "")
                        .param("confirmPassword", "")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));
    }

    @Test
    public void register_whenUsernameAlreadyExistsRedirect() throws Exception {
        this.mockMvc
                .perform(post("/user/register").with(csrf())
                        .param("username", "admin@admin.com")
                        .param("firstName", "Test")
                        .param("lastName", "Test")
                        .param("password", "test")
                        .param("confirmPassword", "test")
                );

        this.mockMvc
                .perform(post("/user/register").with(csrf())
                        .param("username", "admin@admin.com")
                        .param("firstName", "Test")
                        .param("lastName", "Test")
                        .param("password", "test")
                        .param("confirmPassword", "test")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:register"));
    }

    @Test
    @WithMockUser
    public void register_withLoggedInUserShouldReturnErrorView() throws Exception {
        this.mockMvc
                .perform(get("/user/register").with(csrf()))
                .andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/error"));
    }

    @Test
    @WithMockUser(roles = "ROOT")
    public void all_withLoggedInRootUserShouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/user/all").with(csrf()))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("comparator"))
                .andExpect(view().name("user/all"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void all_withLoggedInAdminOrUserShouldReturnCorrectViewAdmin() throws Exception {
        this.mockMvc
                .perform(get("/user/all").with(csrf()))
                .andExpect(view().name("error"));
    }

}
