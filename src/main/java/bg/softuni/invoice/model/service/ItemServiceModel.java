package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.enumerated.VatValue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemServiceModel {

    private String id;
    private String name;
    private BigDecimal price;
    private VatValue vatValue;
    private String imageUrl;
    private int quantity;

}
