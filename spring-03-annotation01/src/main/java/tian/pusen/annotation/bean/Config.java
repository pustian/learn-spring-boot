package tian.pusen.annotation.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tian.pusen.annotation.bean.service.ITestService;
import tian.pusen.annotation.bean.service.impl.TestServiceImpl;

@Configuration
public class Config {
    @Bean
    public ITestService testService() {
        return new TestServiceImpl();
    }
}
