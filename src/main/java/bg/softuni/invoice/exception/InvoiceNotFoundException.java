package bg.softuni.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static bg.softuni.invoice.constant.ErrorMsg.INVOICE_NOT_FOUND;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = INVOICE_NOT_FOUND)
public class InvoiceNotFoundException extends RuntimeException {

    private final int status;

    public InvoiceNotFoundException() {
        this.status = 404;
    }

    public InvoiceNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
