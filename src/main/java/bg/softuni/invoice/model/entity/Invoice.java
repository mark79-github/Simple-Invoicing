package bg.softuni.invoice.model.entity;

import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.DATE_FORMAT_PATTERN;
import static bg.softuni.invoice.constant.ErrorMsg.DATE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.INVOICE_NUMBER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.INVOICE_NUMBER_POSITIVE;
import static bg.softuni.invoice.constant.ErrorMsg.PAYMENT_TYPE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.RECEIVER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.SENDER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.STATUS_TYPE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.USER_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.VALUE_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.VALUE_POSITIVE;
import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

@Setter
@Getter
@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity implements Serializable {

    @Column(name = "date", nullable = false)
    @NotNull(message = DATE_NOT_EMPTY)
    @DateTimeFormat(pattern = DATE_FORMAT_PATTERN)
    private LocalDate date;

    @Column(name = "total_value", nullable = false)
    @NotNull(message = VALUE_NOT_EMPTY)
    @Positive(message = VALUE_POSITIVE)
    private BigDecimal totalValue;

    @ManyToOne(optional = false, cascade = MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(message = USER_NOT_EMPTY)
    private User user;

    @ManyToOne(optional = false, cascade = MERGE)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @NotNull(message = SENDER_NOT_EMPTY)
    private Company sender;

    @ManyToOne(optional = false, cascade = MERGE)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @NotNull(message = RECEIVER_NOT_EMPTY)
    private Company receiver;

    @Column(name = "payment_type", nullable = false)
    @NotNull(message = PAYMENT_TYPE_NOT_EMPTY)
    @Enumerated(EnumType.ORDINAL)
    private PaymentType paymentType;

    @Column(name = "status_type", nullable = false)
    @NotNull(message = STATUS_TYPE_NOT_EMPTY)
    @Enumerated(EnumType.ORDINAL)
    private StatusType statusType;

    @Column(name = "invoice_number", nullable = false)
    @NotNull(message = INVOICE_NUMBER_NOT_EMPTY)
    @Positive(message = INVOICE_NUMBER_POSITIVE)
    private long invoiceNumber;

    @Column(name = "created_on", nullable = false)
    @NotNull(message = DATE_NOT_EMPTY)
    @DateTimeFormat(pattern = DATE_FORMAT_PATTERN)
    private LocalDateTime createdOn;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {DETACH, MERGE, PERSIST, REFRESH})
    @JoinTable(name = "invoices_sales",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "sale_id"))
    private Set<Sale> sales = new HashSet<>();

}
