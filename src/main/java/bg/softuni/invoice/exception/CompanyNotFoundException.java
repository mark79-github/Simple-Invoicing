package bg.softuni.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static bg.softuni.invoice.constant.ErrorMsg.COMPANY_NOT_FOUND;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = COMPANY_NOT_FOUND)
public class CompanyNotFoundException extends RuntimeException {

    private final int status;

    public CompanyNotFoundException() {
        this.status = 404;
    }

    public CompanyNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
