package bg.softuni.invoice.model.bind;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

import static bg.softuni.invoice.constant.ErrorMsg.DATE_FORMAT_PATTERN;
import static bg.softuni.invoice.constant.ErrorMsg.DATE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.DATE_NOW_OR_FUTURE;
import static bg.softuni.invoice.constant.ErrorMsg.PAYMENT_TYPE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.RECEIVER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.SENDER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.VALUE_POSITIVE;

@Setter
@Getter
public class InvoiceAddBindingModel {

    @NotBlank(message = SENDER_NOT_EMPTY)
    private String sender;

    @NotBlank(message = RECEIVER_NOT_EMPTY)
    private String receiver;

    @NotBlank(message = PAYMENT_TYPE_NOT_EMPTY)
    private String paymentType;

    @NotNull(message = VALUE_POSITIVE)
    @Positive(message = VALUE_POSITIVE)
    private BigDecimal totalValue;

    @DateTimeFormat(pattern = DATE_FORMAT_PATTERN)
    @FutureOrPresent(message = DATE_NOW_OR_FUTURE)
    @NotNull(message = DATE_NOT_EMPTY)
    private LocalDate date;

}
