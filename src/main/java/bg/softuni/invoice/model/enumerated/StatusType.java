package bg.softuni.invoice.model.enumerated;

import lombok.Getter;

@Getter
public enum StatusType {

    COMPLETE("Complete"),
    AWAIT("Await");

    private final String type;

    StatusType(String type) {
        this.type = type;
    }

}
