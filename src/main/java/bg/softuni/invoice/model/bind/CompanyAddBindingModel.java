package bg.softuni.invoice.model.bind;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

import static bg.softuni.invoice.constant.ErrorMsg.*;

public class CompanyAddBindingModel {

    @Length(min = STRING_MIN_LENGTH, message = NAME_MIN_LENGTH)
    private String name;

    @Length(min = STRING_MIN_LENGTH, message = ADDRESS_MIN_LENGTH)
    private String address;

    @Pattern(regexp = UNIQUE_IDENTIFIER_REGEX, message = UNIQUE_IDENTIFIER_LENGTH)
    private String uniqueIdentifier;

    public CompanyAddBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
}
