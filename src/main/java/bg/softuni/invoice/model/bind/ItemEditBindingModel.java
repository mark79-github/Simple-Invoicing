package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

import static bg.softuni.invoice.constant.ErrorMsg.IMAGE_SOURCE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.PRICE_POSITIVE;
import static bg.softuni.invoice.constant.ErrorMsg.STRING_MIN_LENGTH;

@Setter
@Getter
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

}
