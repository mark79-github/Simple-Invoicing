package bg.softuni.invoice.service;

import bg.softuni.invoice.repository.LogRepository;
import bg.softuni.invoice.service.impl.LogServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LogServiceTests {

    @InjectMocks
    private LogServiceImpl logService;

    @Mock
    private LogRepository logRepository;

    @Test
    public void getAllLogs_shouldReturnDataCorrectly() {

    }
}
