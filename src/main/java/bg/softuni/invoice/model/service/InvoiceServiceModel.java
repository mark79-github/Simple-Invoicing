package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceServiceModel {

    private String id;
    private LocalDate date;
    private BigDecimal totalValue;
    private UserServiceModel user;
    private CompanyServiceModel sender;
    private CompanyServiceModel receiver;
    private PaymentType paymentType;
    private StatusType statusType;
    private long invoiceNumber;
    private LocalDateTime createdOn;

    public InvoiceServiceModel() {
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

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public CompanyServiceModel getSender() {
        return sender;
    }

    public void setSender(CompanyServiceModel sender) {
        this.sender = sender;
    }

    public CompanyServiceModel getReceiver() {
        return receiver;
    }

    public void setReceiver(CompanyServiceModel receiver) {
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
