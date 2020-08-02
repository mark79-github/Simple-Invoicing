package bg.softuni.invoice.model.service;

import java.time.LocalDateTime;

public class LogServiceModel {

    private String id;
    private String requestURI;
    private String method;
    private LocalDateTime dateTime;
    private String username;

    public LogServiceModel() {
    }

    public LogServiceModel(String requestURI, String method, LocalDateTime dateTime, String username) {
        this.requestURI = requestURI;
        this.method = method;
        this.dateTime = dateTime;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
