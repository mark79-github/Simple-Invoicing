package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static bg.softuni.invoice.constant.ErrorMsg.ADDRESS_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.STRING_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.UNIQUE_IDENTIFIER_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.UNIQUE_IDENTIFIER_REGEX;

@Setter
@Getter
public class CompanyAddBindingModel {

    @Length(min = STRING_MIN_LENGTH, message = NAME_MIN_LENGTH)
    private String name;

    @Length(min = STRING_MIN_LENGTH, message = ADDRESS_MIN_LENGTH)
    private String address;

    @Pattern(regexp = UNIQUE_IDENTIFIER_REGEX, message = UNIQUE_IDENTIFIER_LENGTH)
    private String uniqueIdentifier;

}
