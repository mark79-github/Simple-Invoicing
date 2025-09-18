package bg.softuni.invoice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static bg.softuni.invoice.constant.ErrorMsg.ADDRESS_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.ADDRESS_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.STRING_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.SUPPLIER_NOT_NULL;
import static bg.softuni.invoice.constant.ErrorMsg.UNIQUE_IDENTIFIER_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.UNIQUE_IDENTIFIER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.UNIQUE_IDENTIFIER_REGEX;

@Setter
@Getter
@Entity
@Table(name = "companies")
public class Company extends BaseEntity implements Serializable {

    @Column(name = "name", nullable = false, unique = true)
    @NotEmpty(message = NAME_NOT_EMPTY)
    @Length(min = STRING_MIN_LENGTH, message = NAME_MIN_LENGTH)
    private String name;

    @Column(name = "address", nullable = false)
    @NotEmpty(message = ADDRESS_NOT_EMPTY)
    @Length(min = STRING_MIN_LENGTH, message = ADDRESS_MIN_LENGTH)
    private String address;

    @Column(name = "unique_identifier", nullable = false, unique = true)
    @NotEmpty(message = UNIQUE_IDENTIFIER_NOT_EMPTY)
    @Pattern(regexp = UNIQUE_IDENTIFIER_REGEX, message = UNIQUE_IDENTIFIER_LENGTH)
    private String uniqueIdentifier;

    @Column(name = "supplier", nullable = false)
    @NotNull(message = SUPPLIER_NOT_NULL)
    private boolean supplier;

}
