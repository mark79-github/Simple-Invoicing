package bg.softuni.invoice.model.enumerated;

import lombok.Getter;

@Getter
public enum RoleType {

    ROOT("ROLE_ROOT"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String type;

    RoleType(String type) {
        this.type = type;
    }

}
