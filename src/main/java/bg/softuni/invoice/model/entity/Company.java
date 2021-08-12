package bg.softuni.invoice.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import static bg.softuni.invoice.constant.ErrorMsg.*;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity implements Serializable {

    @Column(name = "name", nullable = false, unique = true)
    @Length(min = STRING_MIN_LENGTH, message = NAME_MIN_LENGTH)
    private String name;

    @Column(name = "address", nullable = false)
    @Length(min = STRING_MIN_LENGTH, message = ADDRESS_MIN_LENGTH)
    private String address;

    @Column(name = "unique_identifier", nullable = false, unique = true)
    @Pattern(regexp = UNIQUE_IDENTIFIER_REGEX, message = UNIQUE_IDENTIFIER_LENGTH)
    private String uniqueIdentifier;

    @Column(name = "supplier", nullable = false)
    @NotNull(message = SUPPLIER_NOT_NULL)
    private boolean supplier;

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

    public boolean isSupplier() {
        return supplier;
    }

    public void setSupplier(boolean supplier) {
        this.supplier = supplier;
    }
}
