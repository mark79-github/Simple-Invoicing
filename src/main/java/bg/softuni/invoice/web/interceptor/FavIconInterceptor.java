package bg.softuni.invoice.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static bg.softuni.invoice.constant.GlobalConstants.FAV_ICON;

@Component
public class FavIconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) {

        if (modelAndView != null) {

            if (!modelAndView.hasView()) {
                return;
            }

            String originalViewName = modelAndView.getViewName();
            if (originalViewName == null || originalViewName.startsWith("redirect:")) {
                return;
            }

            modelAndView.addObject("favIcon", FAV_ICON);
        }

    }
}
