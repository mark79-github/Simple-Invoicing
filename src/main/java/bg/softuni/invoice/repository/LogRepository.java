package bg.softuni.invoice.repository;

import bg.softuni.invoice.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
    @Transactional
    void deleteAllByDateTimeIsBefore(LocalDateTime dateTime);
}
