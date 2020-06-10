package tian.pusen.web.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tian.pusen.web.config.filter.MyFilter;
import tian.pusen.web.config.listner.MyListener;
import tian.pusen.web.config.servlet.MyServlet;

@Configuration
public class WebCoreConfig {

    @Bean
    public ServletRegistrationBean myServlet() {
        return new ServletRegistrationBean<>(new MyServlet());
    }

    @Bean
    public FilterRegistrationBean myFitler() {
        return new FilterRegistrationBean<>(new MyFilter());
    }

    @Bean
    public ServletListenerRegistrationBean myListener() {
        return new ServletListenerRegistrationBean(new MyListener());
    }

}
