### 引入依赖
pom.xml
spring-boot-starter-web

### 编写拦截器
ElapseHandlerInterceptor.java
```java
    // 调用Controller某个方法之前
    preHandle 
    
    // Controller之后调用，视图渲染之前，如果控制器Controller出现了异常，则不会执行此方法
    postHandle
    
    // 不管有没有异常，这个afterCompletion都会被调用，用于资源清理
    afterCompletion 
```
### 配置拦截器
> 注意用 @Configuraition
1. implements WebMvcConfigurer
在扩展类中重写父类的方法即可，这种方法会使用springboot对SpringMVC的自动配置。
> 2, 3 会有问题的
2. @EnableWebMvc + implements WebMvcConfigurer
在扩展类中重写父类的方法即可，这种方法会屏蔽springboot对SpringMVC的自动配置。
3. extends WebMvcConfigurationSupport
在扩展类中重写父类的方法即可，这种方法会屏蔽springboot对SpringMVC的自动配置。

跨域增加报文头设置