package tian.pusen.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import tian.pusen.annotation.bean.service.ITestService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    ApplicationContext ioc;

    @Test
    public void contextLoads() {
        boolean bool = ioc.containsBean("testService");
        System.out.println(bool);
        if(bool) {
            ITestService testService = ioc.getBean("testService", ITestService.class);
            System.out.println(testService.sayHello() + "|");
        }
    }
}
