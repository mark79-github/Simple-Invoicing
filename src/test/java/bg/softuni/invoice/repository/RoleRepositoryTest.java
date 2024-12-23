package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findByAuthority_shouldReturnRole_whenAuthorityExists() {
        Role role = new Role("ADMIN");
        entityManager.persist(role);

        Optional<Role> result = roleRepository.findByAuthority("ADMIN");

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getAuthority());
    }

    @Test
    void findByAuthority_shouldReturnEmpty_whenAuthorityDoesNotExist() {
        Role role = new Role("ADMIN");
        entityManager.persist(role);

        Optional<Role> result = roleRepository.findByAuthority("USER");

        assertFalse(result.isPresent());
    }
}
