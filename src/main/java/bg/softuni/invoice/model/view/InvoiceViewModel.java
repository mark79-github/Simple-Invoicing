package bg.softuni.invoice.model.view;

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
    private Set<SaleViewModel> sales;

}
