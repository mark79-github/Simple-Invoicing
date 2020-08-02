package bg.softuni.invoice.web.interceptor;

import bg.softuni.invoice.model.service.LogServiceModel;
import bg.softuni.invoice.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {

    private final LogService logService;

    @Autowired
    public LoggingInterceptor(LogService logService) {
        this.logService = logService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String username = request.getUserPrincipal() == null
                ? "anonymous"
                : request.getUserPrincipal().getName();

        if (!PathRequest.toStaticResources().atCommonLocations().matches(request)) {
            logService.createLog(
                    new LogServiceModel(requestURI, method, LocalDateTime.now(), username));
        }

        return super.preHandle(request, response, handler);
    }
}
