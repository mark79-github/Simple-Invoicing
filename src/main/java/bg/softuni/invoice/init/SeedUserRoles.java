package bg.softuni.invoice.init;

import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.model.enumerated.RoleType;
import bg.softuni.invoice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SeedUserRoles {

    private final RoleService roleService;

    @Autowired
    public SeedUserRoles(RoleService roleService) {
        this.roleService = roleService;
    }


    @EventListener
    public void seed(ContextRefreshedEvent contextRefreshedEvent) {
        seedRoles();
    }

    private void seedRoles() {
        if (this.roleService.getRoleRepositoryCount() == 0) {
            Arrays.stream(RoleType.values())
                    .map(r -> new Role(r.getType()))
                    .forEach(this.roleService::addRole);
        }
    }


}
