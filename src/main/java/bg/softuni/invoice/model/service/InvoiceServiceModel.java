package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.enumerated.PaymentType;
import bg.softuni.invoice.model.enumerated.StatusType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
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
    private Set<SaleServiceModel> sales;

}
