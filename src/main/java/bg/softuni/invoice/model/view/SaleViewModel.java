package bg.softuni.invoice.model.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class SaleViewModel {

    private String id;
    private String name;
    private int quantity;
    private BigDecimal price;
    private int vatValue;

}
