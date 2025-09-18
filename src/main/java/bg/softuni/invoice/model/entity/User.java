package bg.softuni.invoice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_CORRECT;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.EMAIL_REGEX;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.FIRST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_FIRST_LETTER_UPPERCASE;
import static bg.softuni.invoice.constant.ErrorMsg.LAST_NAME_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.NAME_REGEX;
import static bg.softuni.invoice.constant.ErrorMsg.PASSWORD_MIN_LENGTH;
import static bg.softuni.invoice.constant.ErrorMsg.STRING_MIN_LENGTH;

@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = EMAIL_NOT_EMPTY)
    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_NOT_CORRECT)
    private String username;

    @Getter
    @Column(name = "first_name", nullable = false)
    @Length(min = STRING_MIN_LENGTH, message = FIRST_NAME_MIN_LENGTH)
    @Pattern(regexp = NAME_REGEX, message = FIRST_NAME_FIRST_LETTER_UPPERCASE)
    private String firstName;

    @Getter
    @Column(name = "last_name", nullable = false)
    @Length(min = STRING_MIN_LENGTH, message = LAST_NAME_MIN_LENGTH)
    @Pattern(regexp = NAME_REGEX, message = LAST_NAME_FIRST_LETTER_UPPERCASE)
    private String lastName;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    @Length(min = STRING_MIN_LENGTH, message = PASSWORD_MIN_LENGTH)
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities;

    @Column(name = "enabled")
    private boolean enabled;

    @Transient
    private boolean accountNonExpired;

    @Transient
    private boolean accountNonLocked;

    @Transient
    private boolean credentialsNonExpired;

    @Override
    public Set<Role> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
