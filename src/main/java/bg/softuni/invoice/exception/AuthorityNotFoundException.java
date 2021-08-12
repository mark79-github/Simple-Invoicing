package bg.softuni.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static bg.softuni.invoice.constant.ErrorMsg.ROLE_NOT_FOUND;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ROLE_NOT_FOUND)
public class AuthorityNotFoundException extends RuntimeException {

    private final int status;

    public AuthorityNotFoundException() {
        this.status = 404;
    }

    public AuthorityNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
