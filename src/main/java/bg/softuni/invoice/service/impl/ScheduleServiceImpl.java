package bg.softuni.invoice.service.impl;

import bg.softuni.invoice.model.enumerated.StatusType;
import bg.softuni.invoice.model.service.InvoiceServiceModel;
import bg.softuni.invoice.service.InvoiceService;
import bg.softuni.invoice.service.LogService;
import bg.softuni.invoice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final LogService logService;
    private final InvoiceService invoiceService;

    @Autowired
    public ScheduleServiceImpl(LogService logService, InvoiceService invoiceService) {
        this.logService = logService;
        this.invoiceService = invoiceService;
    }

    //    <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year> <command>

    //    * (all) – it is used to specify that event should happen for every time unit. For example, “*” in the <minute> field – means “for every minute”
    //    ? (any) – it is utilized in the <day-of-month> and <day-of -week> fields to denote the arbitrary value – neglect the field value. For example, if we want to fire a script at “5th of every month” irrespective of what the day of the week falls on that date, then we specify a “?” in the <day-of-week> field
    //    – (range) – it is used to determine the value range. For example, “10-11” in <hour> field means “10th and 11th hours”
    //    , (values) – it is used to specify multiple values. For example, “MON, WED, FRI” in <day-of-week> field means on the days “Monday, Wednesday, and Friday”
    //    / (increments) – it is used to specify the incremental values. For example, a “5/15” in the <minute> field, means at “5, 20, 35 and 50 minutes of an hour”
    //    L (last) – it has different meanings when used in various fields. For example, if it's applied in the <day-of-month> field, then it means last day of the month, i.e. “31st for January” and so on as per the calendar month. It can be used with an offset value, like “L-3“, which denotes the “third to last day of the calendar month”. In the <day-of-week>, it specifies the “last day of a week”. It can also be used with another value in <day-of-week>, like “6L“, which denotes the “last Friday”
    //    W (weekday) – it is used to specify the weekday (Monday to Friday) nearest to a given day of the month. For example, if we specify “10W” in the <day-of-month> field, then it means the “weekday near to 10th of that month”. So if “10th” is a Saturday, then the job will be triggered on “9th”, and if “10th” is a Sunday, then it will trigger on “11th”. If you specify “1W” in the <day-of-month> and if “1st” is Saturday, then the job will be triggered on “3rd” which is Monday, and it will not jump back to the previous month
    //    # – it is used to specify the “N-th” occurrence of a weekday of the month, for example, “3rd Friday of the month” can be indicated as “6#3“

    //    https://crontab.guru/examples.html
    //    https://www.freeformatter.com/cron-expression-generator-quartz.html

    @Override
    @Scheduled(cron = "0 */5 * ? * *")
    public void deleteLogs() {
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(5);
        this.logService.deleteAllLogsByDateTimeIsBefore(localDateTime);
    }

    @Override
    @Scheduled(cron = "0 */5 * ? * *")
    public void changeStatus() {
        List<InvoiceServiceModel> invoices = this.invoiceService.getAllInvoicesStatus(StatusType.AWAIT);
        invoices.forEach(invoiceServiceModel -> this.invoiceService.changeStatus(invoiceServiceModel.getId()));
    }
}
