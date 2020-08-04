package bg.softuni.invoice.model.entity;

import bg.softuni.invoice.model.enumerated.VatValue;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    @Column(name = "name", nullable = false, updatable = false)
    private String name;

    @Column(name = "quantity", nullable = false, updatable = false)
    @Positive
    private int quantity;

    @Column(name = "price", nullable = false, updatable = false)
    @Positive
    private BigDecimal price;

    @Column(name = "vat_value", nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private VatValue vatValue;

    public Sale() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public VatValue getVatValue() {
        return vatValue;
    }

    public void setVatValue(VatValue vatValue) {
        this.vatValue = vatValue;
    }
}
