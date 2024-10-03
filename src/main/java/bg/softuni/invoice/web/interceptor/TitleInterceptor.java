package bg.softuni.invoice.web.interceptor;

import bg.softuni.invoice.web.annotation.PageTitle;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static bg.softuni.invoice.constant.GlobalConstants.APP_TITLE;

@Component
public class TitleInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        if (modelAndView != null && handler instanceof HandlerMethod handlerMethod) {

            if (!modelAndView.hasView()) {
                return;
            }

            String originalViewName = modelAndView.getViewName();
            if (originalViewName == null || originalViewName.startsWith("redirect:")) {
                return;
            }

            PageTitle methodAnnotation = handlerMethod.getMethodAnnotation(PageTitle.class);

            if (methodAnnotation != null) {
                String title = methodAnnotation.value();
                modelAndView.addObject("title", String.format("%s : %s",
                        APP_TITLE,
                        title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase()));
            }
        }
    }
}
