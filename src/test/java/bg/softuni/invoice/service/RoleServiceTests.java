package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.AuthorityNotFoundException;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.repository.RoleRepository;
import bg.softuni.invoice.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RoleServiceTests {

    private Role role;
    private final List<Role> roleList = new ArrayList<>();

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void init() {
        this.roleService = new RoleServiceImpl(roleRepository);

        this.role = new Role("ROLE_ADMIN");

        this.roleList.add(new Role("ROLE_ROOT"));
        this.roleList.add(new Role("ROLE_ADMIN"));
        this.roleList.add(new Role("ROLE_USER"));
    }

    @Test
    void getRoleByName_shouldReturnRoleCorrectly() {
        when(this.roleRepository.findByAuthority("ROLE_ADMIN"))
                .thenReturn(Optional.ofNullable(role));

        Role roleAdmin = this.roleService.getRoleByName("ROLE_ADMIN");

        assertEquals(roleAdmin, this.role);
    }

    @Test
    void getRoleByName_shouldThrowExceptionWhenRoleNotExists() {

        assertThrows(AuthorityNotFoundException.class,
                () -> this.roleService.getRoleByName("ROLE_ADMINISTRATOR"));
    }

    @Test
    void getAllRoles_shouldReturnAllRolesCorrectly() {
        when(this.roleRepository.findAll()).thenReturn(this.roleList);

        Set<Role> roleSet = this.roleService.getAllRoles();

        assertEquals(3, roleSet.size());
        this.roleList.forEach(role -> assertTrue(roleSet.contains(role)));
    }

    @Test
    void getRoleRepositoryCount_shouldReturnResultCorrectly() {
        when(this.roleRepository.count()).thenReturn(Long.valueOf(this.roleList.size()));

        long actual = this.roleService.getRoleRepositoryCount();

        assertEquals(3, actual);
    }


//    @Test
//    public void register_whenUserDataIsValid_shouldSaveTheUser() {
//        String email = "ivanov@abv.bg";
//        String username = UUID.randomUUID().toString();
//        String password = UUID.randomUUID().toString();
//
//        UserRegisterServiceModel userRegisterServiceModel = new UserRegisterServiceModel();
//        userRegisterServiceModel.setUsername(username);
//        userRegisterServiceModel.setEmail(email);
//        userRegisterServiceModel.setPassword(password);
//        userRegisterServiceModel.setConfirmPassword(password);
//
//        assertNull(authService.register(userRegisterServiceModel));
//
//        ArgumentCaptor<User> argumentUser = ArgumentCaptor.forClass(User.class);
//        Mockito.verify(userRepository).save(argumentUser.capture());
//
//        User userDB = argumentUser.getValue();
//
//        assertNotNull(userDB);
//        assertEquals(userRegisterServiceModel.getUsername(), userDB.getUsername());
//        assertEquals(userRegisterServiceModel.getEmail(), userDB.getEmail());
//        assertTrue(hashingService.isPasswordMatch(userRegisterServiceModel.getPassword(),userDB.getPassword() ));
//    }

}
