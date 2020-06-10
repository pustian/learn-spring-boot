package tian.pusen.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(MvcConfigurer.class);

    @Autowired
    AllowOriginIntercepter allowOriginIntercepter;
    @Autowired
    ElaspeHandlerInterceptor elaspeHandlerInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("add intercepotr ");
        registry.addInterceptor(allowOriginIntercepter).addPathPatterns("/**");
        registry.addInterceptor(elaspeHandlerInterceptor).addPathPatterns("/**").excludePathPatterns("/v1/*");;
    }
}
