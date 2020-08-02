package bg.softuni.invoice.model.enumerated;

public enum PaymentType {
    CASH("Cash"),
    TRANSFER("Transfer");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
