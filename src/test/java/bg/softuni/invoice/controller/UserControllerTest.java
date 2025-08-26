package bg.softuni.invoice.controller;

import bg.softuni.invoice.repository.UserRepository;
import bg.softuni.invoice.service.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @AfterEach
    void clear() {
        this.userRepository.deleteAll();
    }

    @Test
    void login_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/user/login"))
                .andExpect(view().name("user/login"));
    }

    @Test
    @WithMockUser
    void login_withLoggedInUserShouldReturnErrorView() throws Exception {
        this.mockMvc
                .perform(get("/user/login"))
                .andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/error"));
    }

    @Test
    void register_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/user/register"))
                .andExpect(model().attributeExists("userRegisterBindingModel"))
                .andExpect(view().name("user/register"));
    }

    @Test
    void register_registerSuccessfullyRedirect() throws Exception {
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

        Assertions.assertEquals(1, this.userRepository.count());
    }

    @Test
    void register_whenBindingResultHasErrorsRedirect() throws Exception {
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
    void register_whenUsernameAlreadyExistsRedirect() throws Exception {

        this.mockMvc
                .perform(post("/user/register").with(csrf())
                        .param("username", "admin@admin.com")
                        .param("firstName", "Admin")
                        .param("lastName", "Admin")
                        .param("password", "admin")
                        .param("confirmPassword", "admin")
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
    void register_withLoggedInUserShouldReturnErrorView() throws Exception {
        this.mockMvc
                .perform(get("/user/register").with(csrf()))
                .andExpect(status().is4xxClientError())
                .andExpect(forwardedUrl("/error"));
    }

    @Test
    @WithMockUser(roles = "ROOT")
    void all_withLoggedInRootUserShouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/user/all").with(csrf()))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attributeExists("comparator"))
                .andExpect(view().name("user/all"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void all_withLoggedInAdminOrUserShouldReturnCorrectViewAdmin() throws Exception {
        this.mockMvc
                .perform(get("/user/all").with(csrf()))
                .andExpect(view().name("error"));
    }

}
