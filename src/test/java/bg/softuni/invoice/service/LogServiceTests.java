package bg.softuni.invoice.service;

import bg.softuni.invoice.model.entity.Log;
import bg.softuni.invoice.model.entity.Role;
import bg.softuni.invoice.model.entity.User;
import bg.softuni.invoice.repository.LogRepository;
import bg.softuni.invoice.service.impl.LogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LogServiceTests {

    private final String LOG_DATA_STRING = UUID.randomUUID().toString();

    private List<Log> logList = new ArrayList<>();
    private Log log;
    private User user;

    @InjectMocks
    private LogServiceImpl logService;

    @Mock
    private LogRepository logRepository;

    @Mock
    private ModelMapper modelMapper;


    @BeforeEach
    public void init() {
        this.logService = new LogServiceImpl(logRepository, modelMapper);

        this.log = new Log();
        log.setRequestURI(LOG_DATA_STRING);
        log.setMethod(LOG_DATA_STRING);
        log.setDateTime(LocalDateTime.now());

        this.user = new User();
        this.user.setUsername("admin@admin.com");
        this.user.setFirstName("Admin");
        this.user.setLastName("Admin");
        this.user.setPassword("admin");
        this.user.setAuthorities(Set.of(new Role("ROLE_ROOT")));

        log.setUser(user);

        this.logList.add(log);
    }


}
