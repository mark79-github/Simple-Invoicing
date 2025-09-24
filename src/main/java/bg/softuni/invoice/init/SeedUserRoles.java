package bg.softuni.invoice.init;

import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.model.enumerated.RoleType;
import bg.softuni.invoice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SeedUserRoles {

    private final RoleService roleService;

    private boolean seedInitialized = false;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        if (!seedInitialized) {
            seedRoles();
            seedInitialized = true;
        }
    }

    private void seedRoles() {
        if (roleService.getRoleRepositoryCount() == 0) {
            Arrays.stream(RoleType.values())
                    .map(r -> new Role(r.getType()))
                    .forEach(roleService::addRole);
        }
    }
}
