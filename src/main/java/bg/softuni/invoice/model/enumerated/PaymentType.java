package bg.softuni.invoice.model.enumerated;

import lombok.Getter;

@Getter
public enum PaymentType {

    CASH("Cash"),
    TRANSFER("Transfer");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }

}
