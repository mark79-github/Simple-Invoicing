package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.enumerated.VatValue;

import java.math.BigDecimal;

public class ItemServiceModel {

    private String id;
    private String name;
    private BigDecimal price;
    private VatValue vatValue;
    private String imageUrl;
    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
