package bg.softuni.invoice.service;

import bg.softuni.invoice.model.enumerated.StatusType;
import bg.softuni.invoice.model.service.InvoiceServiceModel;

import java.util.List;

public interface InvoiceService {
    void addInvoice(InvoiceServiceModel invoiceServiceModel, String principalId);

    List<InvoiceServiceModel> getAllInvoices();

    List<InvoiceServiceModel> getAllInvoicesByUserId(String id);

    void changeStatus(String id);

    List<InvoiceServiceModel> getAllInvoicesStatus(StatusType statusType);
}
