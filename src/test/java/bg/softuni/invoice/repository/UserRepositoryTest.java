package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindByUsername_whenPresent_returnsUser() {
        User user = new User();
        user.setUsername("testuser@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("securePassword123");
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        entityManager.persist(user);

        Optional<User> result = userRepository.findByUsername("testuser@example.com");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser@example.com");
        assertThat(result.get().getFirstName()).isEqualTo("John");
        assertThat(result.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    void testFindByUsername_whenNotPresent_returnsEmpty() {
        Optional<User> result = userRepository.findByUsername("nonexistent@example.com");

        assertThat(result).isNotPresent();
    }
}
