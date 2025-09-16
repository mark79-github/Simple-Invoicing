package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.base.SaleBaseModel;
import bg.softuni.invoice.model.enumerated.VatValue;

public class SaleServiceModel extends SaleBaseModel {

    private VatValue vatValue;

    public VatValue getVatValue() {
        return vatValue;
    }

    public void setVatValue(VatValue vatValue) {
        this.vatValue = vatValue;
    }
}
