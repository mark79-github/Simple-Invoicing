package bg.softuni.invoice.model.enumerated;

public enum StatusType {
    COMPLETE("Complete"),
    AWAIT("Await");

    private final String type;

    StatusType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
