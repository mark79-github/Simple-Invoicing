package bg.softuni.invoice.model.entity;

import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.DATE_FORMAT_PATTERN;
import static bg.softuni.invoice.constant.ErrorMsg.VALUE_POSITIVE;

@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity implements Serializable {

    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = DATE_FORMAT_PATTERN)
    private LocalDate date;

    @Column(name = "total_value", nullable = false)
    @Positive(message = VALUE_POSITIVE)
    @NotNull(message = VALUE_POSITIVE)
    private BigDecimal totalValue;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    private User user;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    private Company sender;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    private Company receiver;

    @Column(name = "payment_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentType paymentType;

    @Column(name = "status_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusType statusType;

    @Column(name = "invoice_number", nullable = false)
    private long invoiceNumber;

    @Column(name = "created_on", nullable = false)
    @DateTimeFormat(pattern = DATE_FORMAT_PATTERN)
    private LocalDateTime createdOn;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "invoices_sales",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "sale_id"))
    private Set<Sale> sales = new HashSet<>();

    public Invoice() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public Company getSender() {
        return sender;
    }

    public void setSender(Company sender) {
        this.sender = sender;
    }

    public Company getReceiver() {
        return receiver;
    }

    public void setReceiver(Company receiver) {
        this.receiver = receiver;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
