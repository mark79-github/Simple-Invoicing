package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static bg.softuni.invoice.constant.ErrorMsg.*;

public class UserLoginBindingModel {

    @NotBlank(message = EMAIL_NOT_EMPTY)
    @Pattern(regexp = EMAIL_REGEX,
            message = EMAIL_NOT_CORRECT)
    private String username;

    @NotBlank(message = PASSWORD_NOT_EMPTY)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
