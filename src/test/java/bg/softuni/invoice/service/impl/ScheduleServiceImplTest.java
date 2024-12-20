package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.service.InvoiceService;
import bg.softuni.invoice.service.LogService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ScheduleServiceImplTest {

    @MockBean
    private LogService logService;

    @SuppressWarnings("unused")
    @MockBean
    private InvoiceService invoiceService;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Test
    void testDeleteLogs_removesLogsOlderThanFiveMinutes() {
        ArgumentCaptor<LocalDateTime> captor = ArgumentCaptor.forClass(LocalDateTime.class);

        scheduleService.deleteLogs();

        verify(logService, Mockito.times(1)).deleteAllLogsByDateTimeIsBefore(captor.capture());

        LocalDateTime capturedDateTime = captor.getValue();
        assertTrue(capturedDateTime.isBefore(LocalDateTime.now().minusMinutes(4))
                && capturedDateTime.isAfter(LocalDateTime.now().minusMinutes(6)));
    }
}
