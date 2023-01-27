package bg.softuni.invoice.web.interceptor;

import bg.softuni.invoice.model.service.LogServiceModel;
import bg.softuni.invoice.model.service.UserServiceModel;
import bg.softuni.invoice.service.LogService;
import bg.softuni.invoice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

import static bg.softuni.invoice.constant.GlobalConstants.ANONYMOUS_USER_USERNAME;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final LogService logService;
    private final UserService userService;

    @Autowired
    public LoggingInterceptor(LogService logService, UserService userService) {
        this.logService = logService;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String username = request.getUserPrincipal() == null
                ? ANONYMOUS_USER_USERNAME
                : request.getUserPrincipal().getName();

        if (!PathRequest.toStaticResources().atCommonLocations().matches(request)) {
            UserServiceModel userServiceModel = this.userService.getUserByName(username).orElse(null);
            this.logService.createLog(new LogServiceModel(requestURI, method, LocalDateTime.now(), userServiceModel));
        }

        return true;
    }
}
