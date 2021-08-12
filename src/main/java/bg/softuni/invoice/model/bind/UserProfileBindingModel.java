package bg.softuni.invoice.model.bind;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static bg.softuni.invoice.constant.ErrorMsg.*;

public class UserProfileBindingModel {

    private String id;

    @NotEmpty(message = EMAIL_NOT_EMPTY)
    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_NOT_CORRECT)
    private String username;

    @Length(min = STRING_MIN_LENGTH, message = FIRST_NAME_MIN_LENGTH)
    @Pattern(regexp = NAME_REGEX, message = FIRST_NAME_FIRST_LETTER_UPPERCASE)
    private String firstName;

    @Length(min = STRING_MIN_LENGTH, message = LAST_NAME_MIN_LENGTH)
    @Pattern(regexp = NAME_REGEX, message = LAST_NAME_FIRST_LETTER_UPPERCASE)
    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
