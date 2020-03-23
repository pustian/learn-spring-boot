# 注解
> @EnableAutoConfiguration, @ComponentScan, @Configuration，所起的作用和@SpringBootApplication是相同的
>@EnableAutoConfiguration、@ComponentScan和@Configuration这3个注解并非是捆绑销售，我们可以将他们任意组合
指定配置文件
### 自定义Config
```java
@Configuration
@EnableAutoConfiguration
@Import({ MyConfig.class, MyAnotherConfig.class })
public class Application {

    public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
    }

}
```
### 指定配置的xml
```java
@Configuration
@EnableAutoConfiguration
@ImportResource(locations={"classpath:applicationContext.xml"})
public class Application {

    public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
    }

}
```
# SpringApplication
### 方式一
```java

@SpringBootApplication 
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
### 方式二
```java

@SpringBootApplication 
public class Application {
    public static void main(String[] args) {
       SpringApplication app = new SpringApplication(Application.class);
       app.run(args);
    }
}
```
### 方式三
```java
@SpringBootApplication 
public class Application {
    public static void main(String[] args) {
         new SpringApplicationBuilder()
             .sources(Parent.class)
             .child(Application.class)
             .run(args);
    }
}
```
> 创建多层次的ApplicationContext （例如，父子关系的Spring的ApplicationContext 和SpringMVC），
> 这时我们可以使用官方提供的另外一种“平滑”的API调用方式来启动工程，
> 即使用SpringApplicationBuilder讲多个方法调用串起来，
> 通过parent() 和 child()来创建多层次的ApplicationContext。
> 如果查看底层代码，可以看到除了调用child()方法略有不同，其他的和前两种方法几乎一样
 

# 修改port
###  配置文件  
####application.yml
```yaml
server:
    port: 8888
```
#### application.properties
```properties
server.port=8888
```
### 启动时带参数
#### -DServer.port
```bash
java -jar -Dserver.port=8083  spring01-1.0.0-RELEASE.jar
```
#### --server.port
```bash
java -jar --server.port=8083  spring01-1.0.0-RELEASE.jar
```
### 代码 
#### 重置DefaultProperties
```java
@SpringBootApplication
public class CustomApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CustomApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }
}
```
#### 重新实现 WebServerFactoryCustomizer
```java
@Component
public class ServerPortCustomizer
        implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(8086);
//        factory.setAddress("");
    }
}
```
#### EmbeddedServletContainerCustomizer 2.X版本再确认
```java
@SpringBootApplication
public class ConfigMain extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {  
    public static void main(String[] args) {  
        SpringApplication.run(ConfigMain.class, args);  
    }  

    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
        return builder.sources(ConfigMain.class);  
    }  

    @Override  
    public void customize(ConfigurableEmbeddedServletContainer container) {
        //指定端口地址
        container.setPort(8090);  
    }  
}
```
生效顺序
> + 内置的server配置
> + 命令行参数
> + property文件
> + @SpringBootApplication配置的主函数


# 修改banner
> 只需要在src/main/resources路径下新建一个banner.txt文件或是banner.git
> 并在banner.txt中填写好你需要打印的文件即可
```html
将文字转成文本文件
http://www.network-science.de/ascii/
将图片转成文本文件
http://www.degraeve.com/img2txt.php 
艺术字 
http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20
```
> 在classpath（即在resources下）中添加中一个banner.txt文件，
> 将或者banner.locationbanner.location设置到此类文件的位置来更改启动时的Banner。
> 如果文件采用了不一样的编码，设置banner.charset（默认是UTF-8）来解决。
> 除了使用文本文件，还可以将banner.gif，banner.jpg或者banner.png图像文件
> 添加到您的classpath中，或者设置一个banner.image.location属性。
> 图像将会被转换成ASCII的表现形式并打印在任何文本banner上方

# 控制banner
```java
@SpringBootApplication
public class GoodthinkApplication {
    //默认开启方法
    public static void main(String[] args) {
        SpringApplication.run(GoodthinkApplication.class, args);
    }
    //关闭方法
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GoodthinkApplication.class);
        //关闭bannar
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);   
    }
      // 自定义启动方式
    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(args);
    }
}
```
> banner.txt中还支持使用一些变量：
> ${spring-boot.version}：SpringBoot的版本
> ${spring-boot.formatted-version}：格式化后的SpringBoot的版本
> ${application.version}：应用版本（在MANIFEST.MF文件中定义）
> ${application.formatted-version}：格式化后的应用版本（在MANIFEST.MF文件中定义） 
> ${application.title}：应用名称（在MANIFEST.MF文件中定义）
> ${Ansi.NAME} (${AnsiColor.NAME}, ${AnsiBackground.NAME}, ${AnsiStyle.NAME})： ANSI控制码

# Junit测试