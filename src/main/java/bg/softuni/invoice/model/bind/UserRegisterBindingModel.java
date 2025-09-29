package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.STRING_MIN_LENGTH;

@Setter
@Getter
public class UserRegisterBindingModel extends UserBindingModel {

    @NotEmpty(message = PASSWORD_NOT_EMPTY)
    @Length(min = STRING_MIN_LENGTH, message = PASSWORD_MIN_LENGTH)
    private String password;

    private String confirmPassword;

}
