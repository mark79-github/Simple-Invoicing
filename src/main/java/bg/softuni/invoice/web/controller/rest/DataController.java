package bg.softuni.invoice.web.controller.rest;

import bg.softuni.invoice.model.entity.Invoice;
import bg.softuni.invoice.model.entity.Sale;
import bg.softuni.invoice.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin("http://localhost")
@RestController
@RequestMapping("/all")
public class DataController {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public DataController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }


    @GetMapping
    public List<Invoice> getInvoices() {
        return this.invoiceRepository.findAll();
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Invoice> getAuthor(@PathVariable String invoiceId) {
        Optional<Invoice> optionalInvoice = this.invoiceRepository.findById(invoiceId);

        return optionalInvoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{invoiceId}/sales")
    public ResponseEntity<Set<Sale>> getAuthorBooks(@PathVariable String invoiceId) {
        Optional<Invoice> optionalInvoice = this.invoiceRepository.findById(invoiceId);

        return optionalInvoice.map(Invoice::getSales)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
