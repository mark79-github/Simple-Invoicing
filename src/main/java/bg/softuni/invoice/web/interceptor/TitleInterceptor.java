package bg.softuni.invoice.web.interceptor;

import bg.softuni.invoice.web.annotation.PageTitle;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static bg.softuni.invoice.constant.GlobalConstants.APP_TITLE;

@Component
public class TitleInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        } else {
            if (handler instanceof HandlerMethod) {

                if (!modelAndView.hasView()) {
                    return;
                }

                String originalViewName = modelAndView.getViewName();
                if (originalViewName == null || originalViewName.startsWith("redirect:")) {
                    return;
                }

                PageTitle methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(PageTitle.class);

                if (methodAnnotation != null) {
                    String title = methodAnnotation.value();
                    modelAndView.addObject("title", String.format("%s : %s",
                            APP_TITLE,
                            title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase()));
                }
            }
        }
    }
}
