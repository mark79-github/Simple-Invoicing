package bg.softuni.invoice.service;

import bg.softuni.invoice.exception.AuthorityNotFoundException;
import bg.softuni.invoice.exception.UserNotFoundException;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.service.UserServiceModel;
import bg.softuni.invoice.repository.UserRepository;
import bg.softuni.invoice.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    private User user;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @BeforeEach
    void init() {

        this.userService = new UserServiceImpl(userRepository, modelMapper, roleService, bCryptPasswordEncoder);

        this.user = new User();
        this.user.setUsername("admin@admin.com");
        this.user.setFirstName("Admin");
        this.user.setLastName("Admin");
        this.user.setPassword("admin");
        this.user.setAuthorities(Set.of(new Role("ROLE_ROOT")));
    }

    @Test
    public void getUserByName_shouldReturnUserCorrectly() {

        when(this.userRepository.findByUsername("admin@admin.com"))
                .thenReturn(Optional.empty());

        Optional<UserServiceModel> userServiceModel = this.userService.getUserByName("admin@admin.com");

        Assert.assertEquals(this.user.getUsername(), userServiceModel.get().getUsername());
        Assert.assertEquals(this.user.getFirstName(), userServiceModel.get().getFirstName());
        Assert.assertEquals(this.user.getLastName(), userServiceModel.get().getLastName());
    }


    @Test
    public void getUserByName_shouldThrowExceptionWhenUserNotExists() {

        Assertions.assertThrows(UsernameNotFoundException.class, () -> this.userService.loadUserByUsername("admin@admin.com"));
    }

    @Test
    public void getUserById_shouldReturnUserCorrectly() {

        when(this.userRepository.findById("admin@admin.com"))
                .thenReturn(Optional.empty());

        UserServiceModel userServiceModel = this.userService.getUserById("admin@admin.com");

        Assert.assertEquals(this.user.getUsername(), userServiceModel.getUsername());
    }

    @Test
    public void getUserById_shouldThrowExceptionWhenUserNotExists() {

        Assertions.assertThrows(UserNotFoundException.class, () -> this.userService.getUserById("admin@admin.com"));
    }
}
