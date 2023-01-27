package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.UserNotFoundException;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.service.UserServiceModel;
import bg.softuni.invoice.repository.UserRepository;
import bg.softuni.invoice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static bg.softuni.invoice.constant.ErrorMsg.USERNAME_NOT_FOUND;
import static bg.softuni.invoice.constant.ErrorMsg.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTests {
    private static final String ID = UUID.randomUUID().toString();
    private static final String USER_NAME = "admin@admin.com";
    private User user;
    private Role role;
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void init() {

        role = new Role("MOCKED_ROLE");

        user = new User();
        user.setId(ID);
        user.setUsername(USER_NAME);
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setPassword("admin");
        user.setAuthorities(new HashSet<>(List.of(role, new Role())));
    }

    @Test
    void getUserByName_shouldThrowExceptionWhenUserNotExists() {

        assertThatThrownBy(() -> this.userService.loadUserByUsername(USER_NAME))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(String.format(USERNAME_NOT_FOUND, USER_NAME));
    }

    @Test
    void getUserById_shouldThrowExceptionWhenUserNotExists() {

        assertThatThrownBy(() -> this.userService.getUserById(ID))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(String.format(USER_NOT_FOUND, ID));
    }

    @Test
    void getUserById_shouldReturnUserCorrectly() {

        doReturn(Optional.of(user)).when(userRepository).findById(anyString());

        UserServiceModel userServiceModel = userService.getUserById(ID);

        assertThat(user.getUsername()).isEqualTo(userServiceModel.getUsername());
    }

    @Test
    void getUserByName_shouldReturnUserCorrectly() {

        doReturn(Optional.of(user)).when(userRepository).findByUsername(anyString());

        Optional<UserServiceModel> userServiceModel = userService.getUserByName(USER_NAME);

        assertThat(userServiceModel).isPresent();
        assertThat(user.getUsername()).isEqualTo(userServiceModel.get().getUsername());
    }

    @Test
    void getAllUsers_shouldReturnUserCorrectly() {

        doReturn(List.of(user)).when(userRepository).findAll();

        List<UserServiceModel> users = userService.getAllUsers();

        assertThat(users).hasSize(1).isInstanceOf(List.class);
    }

    @Test
    void setAdmin_shouldThrowExceptionWhenUserNotExists() {

        assertThatThrownBy(() -> this.userService.setAdmin(ID))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(String.format(USER_NOT_FOUND, ID));
    }

    @Test
    void setUser_shouldThrowExceptionWhenUserNotExists() {

        assertThatThrownBy(() -> this.userService.setUser(ID))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(String.format(USER_NOT_FOUND, ID));
    }

    @Test
    void setUserEnabled_shouldThrowExceptionWhenUserNotExists() {

        assertThatThrownBy(() -> this.userService.setUserEnabled(ID))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(String.format(USER_NOT_FOUND, ID));
    }

    @Test
    void setUserDisabled_shouldThrowExceptionWhenUserNotExists() {

        assertThatThrownBy(() -> this.userService.setUserDisabled(ID))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(String.format(USER_NOT_FOUND, ID));
    }

    @Test
    void setUserEnabled_shouldReturnUserCorrectly() {

        user.setEnabled(false);
        doReturn(Optional.of(user)).when(userRepository).findById(anyString());
        given(userRepository.saveAndFlush(isA(User.class))).willReturn(user);

        userService.setUserEnabled(ID);

        verify(userRepository, times(1)).saveAndFlush(isA(User.class));
        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    void setUserDisabled_shouldReturnUserCorrectly() {

        user.setEnabled(true);
        doReturn(Optional.of(user)).when(userRepository).findById(anyString());
        given(userRepository.saveAndFlush(isA(User.class))).willReturn(user);

        userService.setUserDisabled(ID);

        verify(userRepository, times(1)).saveAndFlush(isA(User.class));
        assertThat(user.isEnabled()).isFalse();
    }

    @Test
    void setUser_shouldReturnUserCorrectly() {

        doReturn(role).when(roleService).getRoleByName(anyString());
        doReturn(Optional.of(user)).when(userRepository).findById(anyString());
        given(userRepository.saveAndFlush(isA(User.class))).willReturn(user);

        userService.setUser(ID);

        verify(userRepository, times(1)).saveAndFlush(isA(User.class));
        assertThat(user.getAuthorities()).hasSize(1);
        assertThat(user.getAuthorities()).contains(role);
    }

    @Test
    void setAdmin_shouldReturnUserCorrectly() {
        Role adminRole = new Role();
        doReturn(adminRole).when(roleService).getRoleByName(anyString());
        doReturn(Optional.of(user)).when(userRepository).findById(anyString());
        given(userRepository.saveAndFlush(isA(User.class))).willReturn(user);

        userService.setAdmin(ID);

        verify(userRepository, times(1)).saveAndFlush(isA(User.class));
        assertThat(user.getAuthorities()).hasSize(3);
        assertThat(user.getAuthorities()).contains(role);
    }

    @Test
    void editUserData_shouldReturnUserCorrectly() {
        given(userRepository.saveAndFlush(isA(User.class))).willReturn(user);

        userService.editUserData(new UserServiceModel());

        verify(userRepository, times(1)).saveAndFlush(isA(User.class));
        assertThat(user.getAuthorities()).contains(role);
    }

    @Test
    void registerUser_whenUserRepositoryIsEmpty_shouldReturnUserCorrectly() {

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setPassword("admin");

        doReturn(role).when(roleService).getRoleByName(anyString());
        doReturn(1L).when(userRepository).count();
        given(userRepository.saveAndFlush(isA(User.class))).willReturn(user);

        userService.registerUser(userServiceModel);

        verify(userRepository, times(1)).saveAndFlush(isA(User.class));
        assertThat(user.getAuthorities()).contains(role);
    }

    @Test
    void registerUser_whenUserRepositoryIsNotEmpty_shouldReturnUserCorrectly() {

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setPassword("admin");

        doReturn(Set.of(role)).when(roleService).getAllRoles();
        doReturn(0L).when(userRepository).count();
        given(userRepository.saveAndFlush(isA(User.class))).willReturn(user);

        userService.registerUser(userServiceModel);

        verify(userRepository, times(1)).saveAndFlush(isA(User.class));
        assertThat(user.getAuthorities()).contains(role);
    }
}
