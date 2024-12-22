package bg.softuni.invoice.web.interceptor;

import bg.softuni.invoice.web.annotation.PageTitle;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import static bg.softuni.invoice.constant.GlobalConstants.APP_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TitleInterceptorTest {

    private static final String EXAMPLE_VIEW = "exampleView";
    private static final String REDIRECT_HOME_VIEW = "redirect:/home";

    @Test
    void postHandle_withPageTitleAnnotation_addsTitleToModel() {
        HttpServletRequest request = createMockRequest();
        HttpServletResponse response = createMockResponse();
        ModelAndView modelAndView = createModelAndView(EXAMPLE_VIEW);

        PageTitle pageTitleAnnotation = Mockito.mock(PageTitle.class);
        Mockito.when(pageTitleAnnotation.value()).thenReturn("testTitle");
        HandlerMethod handlerMethod = createMockHandlerMethodWithAnnotation(pageTitleAnnotation);

        TitleInterceptor interceptor = new TitleInterceptor();
        interceptor.postHandle(request, response, handlerMethod, modelAndView);

        assertThat(modelAndView.getModel()).containsEntry("title", APP_TITLE + " : Testtitle");
    }

    @Test
    void postHandle_withoutPageTitleAnnotation_doesNotAddTitleToModel() {
        HttpServletRequest request = createMockRequest();
        HttpServletResponse response = createMockResponse();
        ModelAndView modelAndView = createModelAndView(EXAMPLE_VIEW);

        HandlerMethod handlerMethod = createMockHandlerMethodWithAnnotation(null);

        TitleInterceptor interceptor = new TitleInterceptor();
        interceptor.postHandle(request, response, handlerMethod, modelAndView);

        assertThat(modelAndView.getModel()).doesNotContainKey("title");
    }

    @Test
    void postHandle_withRedirectView_doesNotAddTitleToModel() {
        HttpServletRequest request = createMockRequest();
        HttpServletResponse response = createMockResponse();
        ModelAndView modelAndView = createModelAndView(REDIRECT_HOME_VIEW);

        HandlerMethod handlerMethod = Mockito.mock(HandlerMethod.class);

        TitleInterceptor interceptor = new TitleInterceptor();
        interceptor.postHandle(request, response, handlerMethod, modelAndView);

        assertThat(modelAndView.getModel()).doesNotContainKey("title");
    }

    @Test
    void postHandle_withNullViewName_doesNotAddTitleToModel() {
        HttpServletRequest request = createMockRequest();
        HttpServletResponse response = createMockResponse();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(null);
        HandlerMethod handlerMethod = Mockito.mock(HandlerMethod.class);

        TitleInterceptor interceptor = new TitleInterceptor();
        interceptor.postHandle(request, response, handlerMethod, modelAndView);

        assertThat(modelAndView.getModel()).doesNotContainKey("title");
    }

    @Test
    void postHandle_withNullModelAndView_doesNotThrowException() {
        HttpServletRequest request = createMockRequest();
        HttpServletResponse response = createMockResponse();

        HandlerMethod handlerMethod = Mockito.mock(HandlerMethod.class);

        TitleInterceptor interceptor = new TitleInterceptor();

        assertDoesNotThrow(() -> interceptor.postHandle(request, response, handlerMethod, null));
        Mockito.verifyNoInteractions(request, response, handlerMethod);
    }

    @Test
    void postHandle_withNonNullModelAndViewWithoutView_doesNotAddTitle() {
        HttpServletRequest request = createMockRequest();
        HttpServletResponse response = createMockResponse();

        ModelAndView modelAndView = Mockito.mock(ModelAndView.class);
        Mockito.when(modelAndView.hasView()).thenReturn(false);
        HandlerMethod handlerMethod = Mockito.mock(HandlerMethod.class);

        TitleInterceptor interceptor = new TitleInterceptor();
        interceptor.postHandle(request, response, handlerMethod, modelAndView);

        Mockito.verify(modelAndView, Mockito.never()).addObject(Mockito.eq("title"), Mockito.any());
    }


    private HttpServletRequest createMockRequest() {
        return Mockito.mock(HttpServletRequest.class);
    }

    private HttpServletResponse createMockResponse() {
        return Mockito.mock(HttpServletResponse.class);
    }

    private ModelAndView createModelAndView(String viewName) {
        return new ModelAndView(viewName);
    }

    private HandlerMethod createMockHandlerMethodWithAnnotation(PageTitle annotation) {
        HandlerMethod handlerMethod = Mockito.mock(HandlerMethod.class);
        Mockito.when(handlerMethod.getMethodAnnotation(PageTitle.class)).thenReturn(annotation);
        return handlerMethod;
    }
}
