package bg.softuni.invoice.model.bind;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

import static bg.softuni.invoice.constant.ErrorMsg.*;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
