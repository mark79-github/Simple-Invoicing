package bg.softuni.invoice.init;

import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.model.enumerated.RoleType;
import bg.softuni.invoice.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SeedUserRolesTest {

    @Autowired
    private SeedUserRoles seedUserRoles;

    @MockitoBean
    private RoleService roleService;

    @MockitoBean
    private ContextRefreshedEvent contextRefreshedEvent;

    @Test
    void testSeedDoesNotAddRolesIfRolesAlreadyExist() {
        when(roleService.getRoleRepositoryCount()).thenReturn(1L);

        seedUserRoles.seed(contextRefreshedEvent);

        verify(roleService, never()).addRole(any(Role.class));
    }

    @Test
    void testSeedAddsRolesWhenDatabaseIsEmpty() {
        when(roleService.getRoleRepositoryCount()).thenReturn(0L);

        seedUserRoles.seed(contextRefreshedEvent);

        verify(roleService, times(RoleType.values().length)).addRole(any(Role.class));
    }
}