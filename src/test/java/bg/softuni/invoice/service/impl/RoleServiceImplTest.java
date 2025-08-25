
package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.exception.AuthorityNotFoundException;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.ROLE_NOT_FOUND;
import static bg.softuni.invoice.model.enumerated.RoleType.ADMIN;
import static bg.softuni.invoice.model.enumerated.RoleType.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testGetRoleRepositoryCount_ReturnsZeroWhenEmpty() {
        when(roleRepository.count()).thenReturn(0L);

        long result = roleService.getRoleRepositoryCount();

        assertEquals(0L, result);
        verify(roleRepository, times(1)).count();
    }

    @Test
    void testGetRoleRepositoryCount_ReturnsNonZeroCount() {
        long expectedCount = 5L;
        when(roleRepository.count()).thenReturn(expectedCount);

        long result = roleService.getRoleRepositoryCount();

        assertEquals(expectedCount, result);
        verify(roleRepository, times(1)).count();
    }

    @Test
    void testAddRole_ShouldSaveRole() {
        Role role = new Role(ADMIN.getType());

        roleService.addRole(role);

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository, times(1)).saveAndFlush(roleCaptor.capture());
        assertEquals(ADMIN.getType(), roleCaptor.getValue().getAuthority());
    }

    @Test
    void testGetAllRoles_ShouldReturnAllRoles() {
        Role role1 = new Role(ADMIN.getType());
        Role role2 = new Role(USER.getType());
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        Set<Role> roles = roleService.getAllRoles();

        assertNotNull(roles);
        assertEquals(2, roles.size());
        assertTrue(roles.contains(role1));
        assertTrue(roles.contains(role2));
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testGetAllRoles_ShouldReturnEmptySetWhenNoRolesExist() {
        when(roleRepository.findAll()).thenReturn(List.of());

        Set<Role> roles = roleService.getAllRoles();

        assertNotNull(roles);
        assertTrue(roles.isEmpty());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testGetRoleByName_ShouldReturnRoleWhenFound() {
        String roleName = ADMIN.getType();
        Role role = new Role(roleName);
        when(roleRepository.findByAuthority(roleName)).thenReturn(Optional.of(role));

        Role result = roleService.getRoleByName(roleName);

        assertNotNull(result);
        assertEquals(roleName, result.getAuthority());
        verify(roleRepository, times(1)).findByAuthority(roleName);
    }

    @Test
    void testGetRoleByName_ShouldThrowExceptionWhenRoleNotFound() {
        String roleName = "ROLE_NON_EXISTENT";
        when(roleRepository.findByAuthority(roleName)).thenReturn(Optional.empty());

        AuthorityNotFoundException exception = assertThrows(
                AuthorityNotFoundException.class,
                () -> roleService.getRoleByName(roleName)
        );
        assertEquals(ROLE_NOT_FOUND, exception.getMessage());
        verify(roleRepository, times(1)).findByAuthority(roleName);
    }
}
