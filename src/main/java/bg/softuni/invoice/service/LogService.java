package bg.softuni.invoice.service;

import bg.softuni.invoice.model.service.LogServiceModel;

import java.time.LocalDateTime;

public interface LogService {
    void createLog(LogServiceModel logServiceModel);

    void deleteAllLogsByDateTimeIsBefore(LocalDateTime localDateTime);
}
