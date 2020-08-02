package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Invoice;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.model.enumerated.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
    @Query("SELECT max(i.invoiceNumber) from Invoice as i")
    Optional<Long> getLastInvoiceNumber();

    List<Invoice> getAllByUser(User user);

    List<Invoice> getAllByStatusType(StatusType statusType);
}
