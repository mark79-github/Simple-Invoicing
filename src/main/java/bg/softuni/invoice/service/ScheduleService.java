package bg.softuni.invoice.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public interface ScheduleService {

    @Async
    void deleteLogs();

    @Async
    void changeStatus();
}
