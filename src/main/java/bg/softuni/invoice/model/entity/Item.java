package bg.softuni.invoice.model.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import static bg.softuni.invoice.constant.ErrorMsg.*;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    @Length(min = STRING_MIN_LENGTH, message = NAME_MIN_LENGTH)
    private String name;

    @Column(name = "price", nullable = false)
    @Positive(message = PRICE_POSITIVE)
    private BigDecimal price;

    @Column(name = "image_url")
    @NotNull(message = IMAGE_SOURCE_NOT_EMPTY)
    private String imageUrl;

    public Item() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
