package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_REGEX;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_REGEX;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.STRING_MIN_LENGTH;

@Setter
@Getter
public class UserRegisterBindingModel {

    @NotEmpty(message = EMAIL_NOT_EMPTY)
    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_NOT_CORRECT)
    private String username;

    @NotEmpty(message = FIRST_NAME_MIN_LENGTH)
    @Length(min = STRING_MIN_LENGTH, message = FIRST_NAME_MIN_LENGTH)
    @Pattern(regexp = NAME_REGEX, message = FIRST_NAME_FIRST_LETTER_UPPERCASE)
    private String firstName;

    @NotEmpty(message = LAST_NAME_MIN_LENGTH)
    @Length(min = STRING_MIN_LENGTH, message = LAST_NAME_MIN_LENGTH)
    @Pattern(regexp = NAME_REGEX, message = LAST_NAME_FIRST_LETTER_UPPERCASE)
    private String lastName;

    @NotEmpty(message = PASSWORD_NOT_EMPTY)
    @Length(min = STRING_MIN_LENGTH, message = PASSWORD_MIN_LENGTH)
    private String password;

    private String confirmPassword;

}
