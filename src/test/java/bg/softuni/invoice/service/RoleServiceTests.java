package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.AuthorityNotFoundException;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.repository.RoleRepository;
import bg.softuni.invoice.service.impl.RoleServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.ROLE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTests {

    private Role role;
    private final List<Role> roleList = new ArrayList<>();

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void init() {

        this.role = new Role("ROLE_ADMIN");

        this.roleList.add(new Role("ROLE_ROOT"));
        this.roleList.add(new Role("ROLE_ADMIN"));
        this.roleList.add(new Role("ROLE_USER"));
    }

    @Test
    void getRoleByName_shouldReturnRoleCorrectly() {

        doReturn(Optional.ofNullable(role)).when(roleRepository).findByAuthority(anyString());

        Role roleAdmin = roleService.getRoleByName("ROLE_ADMIN");

        Assertions.assertThat(roleAdmin).isNotNull();
    }

    @Test
    void getRoleByName_shouldThrowExceptionWhenRoleNotExists() {

        assertThatThrownBy(() -> roleService.getRoleByName("NON_EXISTING_ROLE"))
                .isInstanceOf(AuthorityNotFoundException.class)
                .hasMessageContaining(ROLE_NOT_FOUND);
    }

    @Test
    void getAllRoles_shouldReturnAllRolesCorrectly() {

        doReturn(roleList).when(roleRepository).findAll();

        Set<Role> roleSet = this.roleService.getAllRoles();

        Assertions.assertThat(roleSet).hasSize(3);
    }

    @Test
    void getRoleRepositoryCount_shouldReturnResultCorrectly() {

        doReturn(3L).when(roleRepository).count();

        long actual = this.roleService.getRoleRepositoryCount();

        Assertions.assertThat(actual).isEqualTo(3);
    }

    @Test
    void addRole_shouldCreateRoleCorrectly() {

        given(roleRepository.saveAndFlush(isA(Role.class))).willReturn(role);

        roleService.addRole(role);

        verify(roleRepository, times(1)).saveAndFlush(isA(Role.class));
    }
}
