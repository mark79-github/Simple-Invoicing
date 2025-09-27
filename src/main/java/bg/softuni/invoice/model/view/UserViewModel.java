package bg.softuni.invoice.model.view;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class UserViewModel {

    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private Set<String> authorities = new HashSet<>();
    private boolean enabled;

}
