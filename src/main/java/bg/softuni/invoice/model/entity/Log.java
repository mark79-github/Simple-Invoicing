package bg.softuni.invoice.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

import static bg.softuni.invoice.constant.ErrorMsg.DATE_PAST;

@Entity
@Table(name = "logs")
public class Log extends BaseEntity {

    @Column(name = "request_uri", nullable = false)
    private String requestURI;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "date_time", nullable = false)
    @PastOrPresent(message = DATE_PAST)
    private LocalDateTime dateTime;

    @Column(name = "username", nullable = false)
    private String username;

    public Log() {
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
