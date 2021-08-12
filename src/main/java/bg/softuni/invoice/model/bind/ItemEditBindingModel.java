package bg.softuni.invoice.model.bind;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import static bg.softuni.invoice.constant.ErrorMsg.*;

public class ItemEditBindingModel {

    private String id;

    @Length(min = STRING_MIN_LENGTH, message = NAME_MIN_LENGTH)
    private String name;

    @NotNull(message = PRICE_POSITIVE)
    @Positive(message = PRICE_POSITIVE)
    private BigDecimal price;

    @NotBlank(message = PRICE_POSITIVE)
    private String vatValue;

    @NotBlank(message = IMAGE_SOURCE_NOT_EMPTY)
    private String imageUrl;

    private MultipartFile newImageUrl;

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

    public String getVatValue() {
        return vatValue;
    }

    public void setVatValue(String vatValue) {
        this.vatValue = vatValue;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultipartFile getNewImageUrl() {
        return newImageUrl;
    }

    public void setNewImageUrl(MultipartFile newImageUrl) {
        this.newImageUrl = newImageUrl;
    }
}
