package bg.softuni.invoice.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static bg.softuni.invoice.constant.ErrorMsg.DATE_PAST;
import static bg.softuni.invoice.constant.ErrorMsg.METHOD_NOT_EMPTY;
import static bg.softuni.invoice.constant.ErrorMsg.URI_NOT_EMPTY;

@Setter
@Getter
@Entity
@Table(name = "logs")
public class Log extends BaseEntity {

    @Column(name = "request_uri", nullable = false)
    @NotBlank(message = URI_NOT_EMPTY)
    private String requestURI;

    @Column(name = "method", nullable = false)
    @NotBlank(message = METHOD_NOT_EMPTY)
    private String method;

    @Column(name = "date_time", nullable = false)
    @PastOrPresent(message = DATE_PAST)
    private LocalDateTime dateTime;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User user;

}
