package bg.softuni.invoice.model.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemViewModel {

    private String id;
    private String name;
    private BigDecimal price;
    private String vatValue;
    private String imageUrl;
    private int quantity;

}
