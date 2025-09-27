package bg.softuni.invoice.model.service;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class UserServiceModel {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Set<RoleServiceModel> authorities = new HashSet<>();
    private boolean enabled;

}
