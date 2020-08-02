package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.model.entity.Log;
import bg.softuni.invoice.model.service.LogServiceModel;
import bg.softuni.invoice.repository.LogRepository;
import bg.softuni.invoice.service.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public void createLog(LogServiceModel model) {
        this.logRepository.save(modelMapper.map(model, Log.class));
    }

    @Override
    public void deleteAllLogsByDateTimeIsBefore(LocalDateTime localDateTime) {
        this.logRepository.deleteAllByDateTimeIsBefore(localDateTime);
    }

    @Override
    public List<LogServiceModel> getAllLogs() {
        return this.logRepository
                .findAll()
                .stream()
                .map(log -> this.modelMapper.map(log, LogServiceModel.class))
                .collect(Collectors.toList());
    }
}
