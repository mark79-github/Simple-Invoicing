package bg.softuni.invoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static bg.softuni.invoice.constant.ErrorMsg.ITEM_NOT_FOUND;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ITEM_NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {

    private int status;

    public ItemNotFoundException() {
        this.status = 404;
    }

    public ItemNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
