package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.exception.AuthorityNotFoundException;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.repository.RoleRepository;
import bg.softuni.invoice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static bg.softuni.invoice.constant.ErrorMsg.ROLE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public long getRoleRepositoryCount() {
        return this.roleRepository.count();
    }

    @Override
    public void addRole(Role role) {
        this.roleRepository.saveAndFlush(role);
    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(this.roleRepository.findAll());
    }

    @Override
    public Role getRoleByName(String roleName) {
        return this.roleRepository
                .findByAuthority(roleName)
                .orElseThrow(() -> new AuthorityNotFoundException(ROLE_NOT_FOUND));
    }
}
