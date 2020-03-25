
### @PropertySource
引入一个自己定义的properties
### 此方式为传统springmvc方式
### @ImportResource
引入自定义的xml文件
####  接口 ITestService.java
```java
package tian.pusen.annotation.bean.service;

public interface ITestService {
    String sayHello();
}
```
####  实现类 TestServiceImpl
```java
package tian.pusen.annotation.bean.service.impl;

import tian.pusen.annotation.bean.service.ITestService;

public class TestServiceImpl implements ITestService {
    private String name;

    @Override
    public String sayHello() {
        return "hello " ;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
####  配置文件 beans.xml
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans   xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">

    <bean id="testService" class="tian.pusen.annotation.bean.service.impl.TestServiceImpl">
        <property name="name" value="田圃森" />
    </bean>
</beans>
```
#### 在启动类上增加@ImportResource 
> 此处不要在: 两侧有空格
```java
package tian.pusen.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:beans.xml"})
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
#### 测试类
```java
package tian.pusen.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Autowired
    ApplicationContext ioc;

    @Test
    public void contextLoads() {
        boolean bool = ioc.containsBean("testService");
        System.out.println(bool);
    }
}
```
### springboot推荐使用@Bean代替xml文件配置方式
### @Bean