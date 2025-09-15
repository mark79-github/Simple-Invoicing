package bg.softuni.invoice.model.service;

import bg.softuni.invoice.model.base.UserBaseModel;

import java.util.HashSet;
import java.util.Set;

public class UserServiceModel extends UserBaseModel {

    private String password;
    private Set<RoleServiceModel> authorities = new HashSet<>();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}
