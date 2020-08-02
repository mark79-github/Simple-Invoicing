package bg.softuni.invoice.service;

import bg.softuni.invoice.model.entity.Role;

import java.util.Set;

public interface RoleService {
    long getRoleRepositoryCount();

    void addRole(Role role);

    Set<Role> getAllRoles();

    Role getRoleByName(String roleName);
}
