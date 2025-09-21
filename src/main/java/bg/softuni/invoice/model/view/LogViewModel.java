package bg.softuni.invoice.model.view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class LogViewModel {

    private String id;
    private String requestURI;
    private String method;
    private LocalDateTime dateTime;
    private String user;

}
