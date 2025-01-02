package bg.softuni.invoice.service;

import bg.softuni.invoice.model.entity.Log;
import bg.softuni.invoice.model.service.LogServiceModel;
import bg.softuni.invoice.repository.LogRepository;
import bg.softuni.invoice.service.impl.LogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    private Log log;

    @InjectMocks
    private LogServiceImpl logService;
    @Mock
    private LogRepository logRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void createLog_shouldCreateItemCorrectly() {

        LogServiceModel logServiceModel = new LogServiceModel();
        given(logRepository.save(isA(Log.class))).willReturn(log);

        logService.createLog(logServiceModel);

        verify(logRepository, times(1)).save(isA(Log.class));
    }

    @Test
    void deleteAllLogsByDateTimeIsBefore() {

        doNothing().when(logRepository).deleteAllByDateTimeIsBefore(any(LocalDateTime.class));

        LocalDateTime localDateTime = LocalDateTime.now();
        logService.deleteAllLogsByDateTimeIsBefore(localDateTime);

        verify(logRepository, times(1)).deleteAllByDateTimeIsBefore(localDateTime);
    }
}