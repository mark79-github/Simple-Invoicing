package bg.softuni.invoice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import static bg.softuni.invoice.constant.ErrorMsg.AUTHORITY_NOT_EMPTY;

@Setter
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(name = "authority", nullable = false, updatable = false, unique = true)
    @NotEmpty(message = AUTHORITY_NOT_EMPTY)
    private String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
