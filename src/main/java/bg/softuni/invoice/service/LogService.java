package bg.softuni.invoice.service;

import bg.softuni.invoice.model.service.LogServiceModel;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    void createLog(LogServiceModel model);

    void deleteAllLogsByDateTimeIsBefore(LocalDateTime localDateTime);

    List<LogServiceModel> getAllLogs();
}
