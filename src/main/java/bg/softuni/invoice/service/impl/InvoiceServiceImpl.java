package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.exception.InvoiceNotFoundException;
import bg.softuni.invoice.model.entity.Company;
import bg.softuni.invoice.model.entity.Invoice;
import bg.softuni.invoice.model.entity.Sale;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.enumerated.StatusType;
import bg.softuni.invoice.model.service.*;
import bg.softuni.invoice.repository.InvoiceRepository;
import bg.softuni.invoice.service.InvoiceService;
import bg.softuni.invoice.service.ItemService;
import bg.softuni.invoice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.ErrorMsg.INVOICE_NOT_FOUND;
import static bg.softuni.invoice.constant.ErrorMsg.USERNAME_NOT_FOUND;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ItemService itemService;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ModelMapper modelMapper, UserService userService, ItemService itemService) {
        this.invoiceRepository = invoiceRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public void addInvoice(InvoiceServiceModel invoiceServiceModel, String username) {

        Invoice invoice = this.modelMapper.map(invoiceServiceModel, Invoice.class);

        Company company = this.modelMapper.map(invoiceServiceModel.getSender(), Company.class);
        invoice.setSender(company);

        company = this.modelMapper.map(invoiceServiceModel.getReceiver(), Company.class);
        invoice.setReceiver(company);

        if (invoiceServiceModel.getPaymentType().name().equals("CASH")) {
            invoice.setStatusType(StatusType.COMPLETE);
        } else {
            invoice.setStatusType(StatusType.AWAIT);
        }

        long lastInvoiceNumber = this.invoiceRepository.getLastInvoiceNumber().orElse(0L) + 1;
        invoice.setInvoiceNumber(lastInvoiceNumber);

        UserServiceModel userServiceModel = this.userService.getUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USERNAME_NOT_FOUND, username)));
//        UserServiceModel userServiceModel = this.userService.getUserById(principalId);
        User user = this.modelMapper.map(userServiceModel, User.class);
        invoice.setUser(user);

        invoice.setCreatedOn(LocalDateTime.now());

        Set<Sale> sales = invoiceServiceModel.getSales()
                .stream()
                .map(saleServiceModel -> this.modelMapper.map(saleServiceModel, Sale.class))
                .collect(Collectors.toSet());

//        Set<SaleServiceModel> saleServiceModelSet = invoiceServiceModel.getSales()
//                .stream()
//                .map(saleServiceModel -> {
//                    saleServiceModel.setQuantity(stringIntegerEntry.getValue());
//                    ItemServiceModel itemServiceModel = this.itemService.getItemById(stringIntegerEntry.getKey());
//                    saleServiceModel.setName(itemServiceModel.getName());
//                    return saleServiceModel;
//                })
//                .collect(Collectors.toSet());
//        Set<Sale> sales = saleServiceModelSet.stream()
//                .map(saleServiceModel -> this.modelMapper.map(saleServiceModel, Sale.class))
//                .collect(Collectors.toSet());

        invoice.setSales(sales);

        this.invoiceRepository.saveAndFlush(invoice);
    }

    @Override
    public List<InvoiceServiceModel> getAllInvoices() {
        return this.invoiceRepository
                .findAll()
                .stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceServiceModel> getAllInvoicesByUserId(String id) {

        UserServiceModel userServiceModel = this.userService.getUserById(id);
        User user = this.modelMapper.map(userServiceModel, User.class);
        List<Invoice> invoices = this.invoiceRepository.getAllByUser(user);

        return invoices.stream()
                .map(this::apply)
                .collect(Collectors.toList());
    }

    @Override
    public void changeStatus(String id) {
        Invoice invoice = this.invoiceRepository
                .findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException(INVOICE_NOT_FOUND));
        invoice.setStatusType(StatusType.COMPLETE);
        this.invoiceRepository.saveAndFlush(invoice);
    }

    @Override
    public List<InvoiceServiceModel> getAllInvoicesStatus(StatusType statusType) {
        return this.invoiceRepository
                .getAllByStatusType(statusType)
                .stream()
                .map(invoice -> this.modelMapper.map(invoice, InvoiceServiceModel.class))
                .collect(Collectors.toList());
    }

    private InvoiceServiceModel apply(Invoice invoice) {
        InvoiceServiceModel invoiceServiceModel = this.modelMapper.map(invoice, InvoiceServiceModel.class);
        CompanyServiceModel senderServiceModel = this.modelMapper.map(invoice.getSender(), CompanyServiceModel.class);
        CompanyServiceModel receiverServiceModel = this.modelMapper.map(invoice.getReceiver(), CompanyServiceModel.class);
        invoiceServiceModel.setSender(senderServiceModel);
        invoiceServiceModel.setReceiver(receiverServiceModel);
        return invoiceServiceModel;
    }
}
