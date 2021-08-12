package bg.softuni.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static bg.softuni.invoice.constant.ErrorMsg.USERNAME_NOT_FOUND;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = USERNAME_NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final int status;

    public UserNotFoundException() {
        this.status = 404;
    }

    public UserNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
