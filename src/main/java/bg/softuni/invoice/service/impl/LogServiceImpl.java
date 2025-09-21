package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.model.entity.Log;
import bg.softuni.invoice.model.service.LogServiceModel;
import bg.softuni.invoice.repository.LogRepository;
import bg.softuni.invoice.service.LogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createLog(LogServiceModel logServiceModel) {
        Log log = this.modelMapper.map(logServiceModel, Log.class);
        this.logRepository.save(log);
    }

    @Override
    public void deleteAllLogsByDateTimeIsBefore(LocalDateTime localDateTime) {
        this.logRepository.deleteAllByDateTimeIsBefore(localDateTime);
    }
}
