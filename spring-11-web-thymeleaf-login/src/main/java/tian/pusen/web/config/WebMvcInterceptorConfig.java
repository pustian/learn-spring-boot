package tian.pusen.web.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tian.pusen.web.config.handler.LogHandlerInterceptor;
import tian.pusen.web.config.handler.LoginHandlerInterceptor;

@Component
public class WebMvcInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    // 注意顺序
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogHandlerInterceptor());
        registry.addInterceptor(new LoginHandlerInterceptor() )
                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/index.html", "/user/login", "/main.html");
                .excludePathPatterns("/", "/index.html", "/user/login", "login");
    }
}
