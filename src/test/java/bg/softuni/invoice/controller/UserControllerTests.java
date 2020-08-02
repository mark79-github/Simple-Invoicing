package bg.softuni.invoice.controller;

import bg.softuni.invoice.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void login_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/user/login"))
                .andExpect(view().name("user/login"));
    }

    @Test
    public void register_shouldReturnCorrectView() throws Exception {
        this.mockMvc
                .perform(get("/user/register"))
                .andExpect(view().name("user/register"));
    }

    @Test
    public void registerPost_registerUserCorrectly() throws Exception {

        this.mockMvc
                .perform(post("/user/register")
                        .param("username", "admin@admin.com")
                        .param("firstName", "Admin")
                        .param("lastName", "Admin")
                        .param("password", "admin")
                        .param("confirmPassword", "admin")
                );

        Assert.assertEquals(1, this.userRepository.count());
    }

    @Test
    public void register_registerSuccessfullyRedirect() throws Exception {
        this.mockMvc
                .perform(post("/user/register")
                        .param("username", "admin@admin.com")
                        .param("password", "admin")
                        .param("firstName", "Admin")
                        .param("lastName", "Admin")
                        .param("confirmPassword", "admin")
                ).andExpect(view().name("redirect:login"));
    }

}
