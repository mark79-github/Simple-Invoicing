package bg.softuni.invoice.model.entity;

import bg.softuni.invoice.model.enumerated.VatValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;

import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PRICE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.PRICE_POSITIVE;
import static bg.softuni.invoice.constant.ErrorMsg.VAT_VALUE_NOT_EMPTY;

@Setter
@Getter
@Entity
@Table(name = "sales")
public class Sale extends BaseEntity implements Serializable {

    @Column(name = "name", nullable = false, updatable = false)
    @NotEmpty(message = NAME_MIN_LENGTH)
    @Length(min = 3, message = NAME_MIN_LENGTH)
    private String name;

    @Column(name = "quantity", nullable = false, updatable = false)
    @Positive
    private int quantity;

    @Column(name = "price", nullable = false, updatable = false)
    @NotNull(message = PRICE_NOT_EMPTY)
    @Positive(message = PRICE_POSITIVE)
    private BigDecimal price;

    @Column(name = "vat_value", nullable = false, updatable = false)
    @NotNull(message = VAT_VALUE_NOT_EMPTY)
    @Enumerated(EnumType.ORDINAL)
    private VatValue vatValue;

}
