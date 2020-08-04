package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.model.entity.Log;
import bg.softuni.invoice.model.service.LogServiceModel;
import bg.softuni.invoice.model.view.LogViewModel;
import bg.softuni.invoice.repository.LogRepository;
import bg.softuni.invoice.service.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static bg.softuni.invoice.constant.GlobalConstants.ANONYMOUS_USER_USERNAME;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createLog(LogServiceModel logServiceModel) {
        Log log = this.modelMapper.map(logServiceModel, Log.class);
        this.logRepository.save(log);
    }

    @Override
    public void deleteAllLogsByDateTimeIsBefore(LocalDateTime localDateTime) {
        this.logRepository.deleteAllByDateTimeIsBefore(localDateTime);
    }

    @Override
    public List<LogViewModel> getAllLogs() {
        return this.logRepository
                .findAll()
                .stream()
                .map(logServiceModel -> {
                    LogViewModel logViewModel = this.modelMapper.map(logServiceModel, LogViewModel.class);
                    if (logServiceModel.getUser() != null) {
                        logViewModel.setUser(String.format("%s %s",
                                logServiceModel.getUser().getFirstName(),
                                logServiceModel.getUser().getLastName()));
                    } else {
                        logViewModel.setUser(ANONYMOUS_USER_USERNAME);
                    }
                    return logViewModel;
                })
                .collect(Collectors.toList());
    }

}
