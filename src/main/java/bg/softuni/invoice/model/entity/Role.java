package bg.softuni.invoice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import static bg.softuni.invoice.constant.ErrorMsg.AUTHORITY_NOT_EMPTY;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(name = "authority", nullable = false, updatable = false, unique = true)
    @NotEmpty(message = AUTHORITY_NOT_EMPTY)
    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
