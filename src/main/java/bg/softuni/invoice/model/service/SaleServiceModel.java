package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.enumerated.VatValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SaleServiceModel {

    private String name;
    private int quantity;
    private BigDecimal price;
    private VatValue vatValue;

}
