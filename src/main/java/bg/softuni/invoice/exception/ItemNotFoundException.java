package bg.softuni.invoice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static bg.softuni.invoice.constant.ErrorMsg.ITEM_NOT_FOUND;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ITEM_NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {

    private final int status;

    public ItemNotFoundException(String message) {
        super(message);
        this.status = 404;
    }
}
