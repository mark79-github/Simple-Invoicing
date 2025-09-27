package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_REGEX;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_NOT_EMPTY;

@Setter
@Getter
public class UserLoginBindingModel {

    @NotBlank(message = EMAIL_NOT_EMPTY)
    @Pattern(regexp = EMAIL_REGEX,
            message = EMAIL_NOT_CORRECT)
    private String username;

    @NotBlank(message = PASSWORD_NOT_EMPTY)
    private String password;

}
