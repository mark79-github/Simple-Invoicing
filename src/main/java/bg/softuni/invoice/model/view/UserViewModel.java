package bg.softuni.invoice.model.view;

import bg.softuni.invoice.model.base.UserBaseModel;

import java.util.HashSet;
import java.util.Set;

public class UserViewModel extends UserBaseModel {

    private Set<String> authorities = new HashSet<>();

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
