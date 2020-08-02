package bg.softuni.invoice.model.view;

import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceViewModel {

    private String id;
    private LocalDate date;
    private BigDecimal totalValue;
    private UserViewModel user;
    private CompanyViewModel sender;
    private CompanyViewModel receiver;
    private PaymentType paymentType;
    private StatusType statusType;
    private long invoiceNumber;
    private LocalDateTime createdOn;

    public InvoiceViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public UserViewModel getUser() {
        return user;
    }

    public void setUser(UserViewModel user) {
        this.user = user;
    }

    public CompanyViewModel getSender() {
        return sender;
    }

    public void setSender(CompanyViewModel sender) {
        this.sender = sender;
    }

    public CompanyViewModel getReceiver() {
        return receiver;
    }

    public void setReceiver(CompanyViewModel receiver) {
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
