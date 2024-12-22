package bg.softuni.invoice.web.interceptor;

import bg.softuni.invoice.constant.GlobalConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavIconInterceptorTest {

    private static final String SOME_VIEW = "someView";
    private static final String REDIRECT_VIEW = "redirect:/someRedirect";

    @InjectMocks
    private FavIconInterceptor favIconInterceptor;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private Object handler;

    @Test
    void givenValidViewName_whenPostHandle_withNonNullModelAndView_thenFavIconAdded() {
        ModelAndView modelAndView = createModelAndView(SOME_VIEW);

        favIconInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

        assertEquals(GlobalConstants.FAV_ICON, modelAndView.getModel().get("favIcon"));
    }

    @Test
    void givenNullModelAndView_whenPostHandle_thenNoExceptionThrown() {
        assertDoesNotThrow(() ->
                favIconInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, null));
    }

    @Test
    void givenNullViewName_whenPostHandle_withNonNullModelAndView_thenFavIconNotAdded() {
        ModelAndView modelAndView = createModelAndView(null);

        favIconInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

        assertNull(modelAndView.getModel().get("favIcon"));
    }

    @Test
    void givenRedirectViewName_whenPostHandle_withNonNullModelAndView_thenFavIconNotAdded() {
        ModelAndView modelAndView = createModelAndView(REDIRECT_VIEW);

        favIconInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

        assertNull(modelAndView.getModel().get("favIcon"));
    }

    @Test
    void givenModelAndViewWithoutView_whenPostHandle_thenFavIconNotAdded() {
        ModelAndView modelAndView = mock(ModelAndView.class);
        when(modelAndView.hasView()).thenReturn(false);

        favIconInterceptor.postHandle(httpServletRequest, httpServletResponse, handler, modelAndView);

        verify(modelAndView, never()).addObject("favIcon", GlobalConstants.FAV_ICON);
    }

    private ModelAndView createModelAndView(String viewName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
