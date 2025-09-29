package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_NOT_EMPTY;

@Setter
@Getter
public class UserLoginBindingModel extends UserBaseBindingModel {

    @NotBlank(message = PASSWORD_NOT_EMPTY)
    private String password;

}
