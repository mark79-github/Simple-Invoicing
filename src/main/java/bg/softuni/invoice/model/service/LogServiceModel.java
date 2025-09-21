package bg.softuni.invoice.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class LogServiceModel {

    private String id;
    private String requestURI;
    private String method;
    private LocalDateTime dateTime;
    private UserServiceModel user;

    public LogServiceModel(String requestURI, String method, LocalDateTime dateTime, UserServiceModel user) {
        this.requestURI = requestURI;
        this.method = method;
        this.dateTime = dateTime;
        this.user = user;
    }

}
