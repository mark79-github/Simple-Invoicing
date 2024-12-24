package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Log;
import bg.softuni.invoice.model.entity.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LogRepositoryTest {

    private static final LocalDateTime TWO_DAYS_AGO = LocalDateTime.now().minusDays(2);
    private static final LocalDateTime THREE_DAYS_AGO = LocalDateTime.now().minusDays(3);
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final LocalDateTime ONE_DAY_AGO = LocalDateTime.now().minusDays(1);

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void deleteAllByDateTimeIsBefore_removesLogsBeforeGivenDate() {
        createAndPersistLog("/api/logs/1", "GET", TWO_DAYS_AGO, null);
        createAndPersistLog("/api/logs/2", "POST", NOW, null);

        logRepository.deleteAllByDateTimeIsBefore(ONE_DAY_AGO);

        List<Log> remainingLogs = logRepository.findAll();

        assertThat(remainingLogs).hasSize(1);
        assertThat(remainingLogs.get(0).getRequestURI()).isEqualTo("/api/logs/2");
    }

    @Test
    void deleteAllByDateTimeIsBefore_noEffect_whenNoLogsBeforeGivenDate() {
        createAndPersistLog("/api/logs/1", "GET", NOW, null);

        logRepository.deleteAllByDateTimeIsBefore(ONE_DAY_AGO);

        List<Log> remainingLogs = logRepository.findAll();

        assertThat(remainingLogs).hasSize(1);
        assertThat(remainingLogs.get(0).getRequestURI()).isEqualTo("/api/logs/1");
    }

    @Test
    void deleteAllByDateTimeIsBefore_removesAllLogs_whenAllBeforeGivenDate() {
        createAndPersistLog("/api/logs/1", "GET", THREE_DAYS_AGO, null);
        createAndPersistLog("/api/logs/2", "POST", TWO_DAYS_AGO, null);

        logRepository.deleteAllByDateTimeIsBefore(ONE_DAY_AGO);

        List<Log> remainingLogs = logRepository.findAll();

        assertThat(remainingLogs).isEmpty();
    }

    private void createAndPersistLog(String requestURI, String method, LocalDateTime dateTime, User user) {
        Log log = new Log();
        log.setRequestURI(requestURI);
        log.setMethod(method);
        log.setDateTime(dateTime);
        log.setUser(user);
        entityManager.persist(log);
    }
}
