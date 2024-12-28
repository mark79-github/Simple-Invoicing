package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static bg.softuni.invoice.constant.ErrorMsg.IMAGE_SOURCE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PRICE_POSITIVE;
import static bg.softuni.invoice.constant.ErrorMsg.STRING_MIN_LENGTH;

public class ItemEditBindingModel {

    private String id;

    @NotBlank(message = NAME_MIN_LENGTH)
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
