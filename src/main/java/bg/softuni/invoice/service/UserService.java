package bg.softuni.invoice.service;

import bg.softuni.invoice.model.service.UserServiceModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    void registerUser(UserServiceModel userServiceModel);

    List<UserServiceModel> getAllUsers();

    void editUserData(UserServiceModel userServiceModel);

    @PreAuthorize("hasRole('ROOT')")
    void setAdmin(String id);

    @PreAuthorize("hasRole('ROOT')")
    void setUser(String id);

    @PreAuthorize("hasRole('ROOT')")
    void setUserEnabled(String id);

    @PreAuthorize("hasRole('ROOT')")
    void setUserDisabled(String id);

    Optional<UserServiceModel> getUserByName(String username);

    UserServiceModel getUserById(String id);
}
