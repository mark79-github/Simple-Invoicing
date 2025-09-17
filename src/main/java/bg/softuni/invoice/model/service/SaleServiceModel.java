package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.enumerated.VatValue;

import java.math.BigDecimal;

public class SaleServiceModel {

    private String name;
    private int quantity;
    private BigDecimal price;
    private VatValue vatValue;

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
