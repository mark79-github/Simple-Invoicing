package bg.softuni.invoice.web.interceptor;

import bg.softuni.invoice.model.service.LogServiceModel;
import bg.softuni.invoice.model.service.UserServiceModel;
import bg.softuni.invoice.service.LogService;
import bg.softuni.invoice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

import static bg.softuni.invoice.constant.GlobalConstants.ANONYMOUS_USER_USERNAME;

@Component
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final LogService logService;
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
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
