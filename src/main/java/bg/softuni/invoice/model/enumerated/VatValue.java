package bg.softuni.invoice.model.enumerated;

public enum VatValue {
    ZERO(0),
    NINE(9),
    TWENTY(20);

    private final int value;

    VatValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
