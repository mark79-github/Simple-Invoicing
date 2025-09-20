package bg.softuni.invoice.config;

import bg.softuni.invoice.web.interceptor.FavIconInterceptor;
import bg.softuni.invoice.web.interceptor.LoggingInterceptor;
import bg.softuni.invoice.web.interceptor.TitleInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final TitleInterceptor titleInterceptor;
    private final FavIconInterceptor faviconInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
        registry.addInterceptor(this.loggingInterceptor);
    }
}
