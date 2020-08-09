package bg.softuni.invoice.web.controller;

import bg.softuni.invoice.model.entity.Invoice;
import bg.softuni.invoice.model.entity.Sale;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost")
@RestController
@RequestMapping("/api/invoice")
public class InvoiceRestController {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceRestController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Invoice> getInvoices(@AuthenticationPrincipal User principal) {

        if (principal.getAuthorities().size() == 1) {
            return this.invoiceRepository.findAll()
                    .stream()
                    .filter(invoice -> invoice.getUser().getUsername().equals(principal.getUsername()))
                    .collect(Collectors.toList());
        } else {
            return this.invoiceRepository.findAll();
        }

    }

    @GetMapping("/{invoiceId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Invoice> getInvoice(@PathVariable String invoiceId) {
        Optional<Invoice> optionalInvoice = this.invoiceRepository.findById(invoiceId);

        return optionalInvoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{invoiceId}/sales")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Set<Sale>> getInvoiceSales(@PathVariable String invoiceId) {
        Optional<Invoice> optionalInvoice = this.invoiceRepository.findById(invoiceId);

        return optionalInvoice.map(Invoice::getSales)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
