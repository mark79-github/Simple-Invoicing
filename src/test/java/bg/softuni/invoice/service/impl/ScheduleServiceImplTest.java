package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.model.enumerated.StatusType;
import bg.softuni.invoice.model.service.InvoiceServiceModel;
import bg.softuni.invoice.service.InvoiceService;
import bg.softuni.invoice.service.LogService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ScheduleServiceImplTest {

    @MockBean
    private LogService logService;

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

    @Test
    void testChangeStatus_changesStatusOfInvoicesWithAwaitStatus() {
        InvoiceServiceModel invoice1 = new InvoiceServiceModel();
        invoice1.setId("1");
        invoice1.setStatusType(StatusType.AWAIT);

        InvoiceServiceModel invoice2 = new InvoiceServiceModel();
        invoice2.setId("2");
        invoice2.setStatusType(StatusType.AWAIT);

        List<InvoiceServiceModel> mockInvoices = List.of(invoice1, invoice2);

        Mockito.when(invoiceService.getAllInvoicesStatus(StatusType.AWAIT)).thenReturn(mockInvoices);

        scheduleService.changeStatus();

        Mockito.verify(invoiceService, Mockito.times(1)).getAllInvoicesStatus(StatusType.AWAIT);
        Mockito.verify(invoiceService, Mockito.times(2)).changeStatus(Mockito.anyString());
        Mockito.verify(invoiceService).changeStatus("1");
        Mockito.verify(invoiceService).changeStatus("2");
    }
}
