package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.AuthorityNotFoundException;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.repository.RoleRepository;
import bg.softuni.invoice.service.impl.RoleServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static bg.softuni.invoice.constant.ErrorMsg.ROLE_NOT_FOUND;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleServiceTests {

    private Role role;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;

    @BeforeEach
    void init() {
        this.roleService = new RoleServiceImpl(roleRepository);

        this.role = new Role();
        this.role.setId("UUID");
        this.role.setAuthority("ROLE_ADMIN");
    }

    @Test
    public void returnRoleIfExistsCorrectly() {
//    Arrange
        when(this.roleRepository.findByAuthority("ROLE_ADMIN"))
                .thenReturn(Optional.ofNullable(role))
                .thenThrow(new AuthorityNotFoundException(ROLE_NOT_FOUND));
//    Act
        Role roleAdmin = this.roleService.getRoleByName("ROLE_ADMIN");
//    Assert
        Assert.assertEquals(roleAdmin, this.role);
    }

    @Test
    public void shouldThrowExceptionWhenSearchForNonExistingRole() {

        Assertions.assertThrows(AuthorityNotFoundException.class, () -> {
            this.roleService.getRoleByName("ROLE_ADMINISTRATOR");
        });

    }


}
