package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.exception.UserNotFoundException;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.service.RoleServiceModel;
import bg.softuni.invoice.model.service.UserServiceModel;
import bg.softuni.invoice.repository.UserRepository;
import bg.softuni.invoice.service.RoleService;
import bg.softuni.invoice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.ErrorMsg.USERNAME_NOT_FOUND;
import static bg.softuni.invoice.constant.ErrorMsg.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        if (this.userRepository.count() == 0) {
            user.setAuthorities(this.roleService.getAllRoles());
            user.setEnabled(true);
        } else {
            user.getAuthorities().add(this.roleService.getRoleByName("ROLE_USER"));
        }

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> {
                    UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
                    Set<RoleServiceModel> authorities = user.getAuthorities()
                            .stream()
                            .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                            .collect(Collectors.toSet());
                    userServiceModel.setAuthorities(authorities);
                    return userServiceModel;
                })
                .toList();
    }

    @Override
    public void editUserData(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void setAdmin(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));

        user.getAuthorities().add(this.roleService.getRoleByName("ROLE_ADMIN"));

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void setUser(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));

        user.getAuthorities().clear();
        user.getAuthorities().add(this.roleService.getRoleByName("ROLE_USER"));

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void setUserEnabled(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));

        user.setEnabled(true);

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void setUserDisabled(String id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));

        user.setEnabled(false);

        this.userRepository.saveAndFlush(user);
    }

    @Override
    public Optional<UserServiceModel> getUserByName(String username) {
        return this.userRepository
                .findByUsername(username)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class));
    }

    @Override
    public UserServiceModel getUserById(String id) {
        return this.userRepository
                .findById(id)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND, id)));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .map(user -> this.modelMapper.map(user, UserDetails.class))
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username)));
    }
}
