package bg.softuni.invoice.model.view;

import bg.softuni.invoice.model.base.SaleBaseModel;

public class SaleViewModel extends SaleBaseModel {

    private String id;
    private int vatValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVatValue() {
        return vatValue;
    }

    public void setVatValue(int vatValue) {
        this.vatValue = vatValue;
    }
}
