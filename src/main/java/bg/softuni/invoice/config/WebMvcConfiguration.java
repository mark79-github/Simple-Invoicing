package bg.softuni.invoice.config;

import bg.softuni.invoice.web.interceptor.FavIconInterceptor;
import bg.softuni.invoice.web.interceptor.LoggingInterceptor;
import bg.softuni.invoice.web.interceptor.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final TitleInterceptor titleInterceptor;
    private final FavIconInterceptor faviconInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Autowired
    public WebMvcConfiguration(TitleInterceptor titleInterceptor, FavIconInterceptor faviconInterceptor, LoggingInterceptor loggingInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
        this.loggingInterceptor = loggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
        registry.addInterceptor(this.loggingInterceptor);
    }
}
