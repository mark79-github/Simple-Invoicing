package bg.softuni.invoice.service;

import bg.softuni.invoice.model.service.LogServiceModel;
import bg.softuni.invoice.model.view.LogViewModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.GlobalConstants.ANONYMOUS_USER_USERNAME;

public interface LogService {
    void createLog(LogServiceModel logServiceModel);

    void deleteAllLogsByDateTimeIsBefore(LocalDateTime localDateTime);

    List<LogViewModel> getAllLogs();
}
