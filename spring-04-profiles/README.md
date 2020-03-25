Profile 时springmvc引入的一个多环境配置
https://www.cnblogs.com/zwwhnly/p/11350018.html
# 多环境代码书写
###  `多properties/yml文件`或是 `yaml文档块`
```bash
src/main/resources
application.yml
application-dev.yml
application-sit.yml
application-uat.yml
application-prod.yml
```
### java代码中
java代码中的代码生效可以支持方法和@Bean一起使用
```java
@Profile("dev")
```
### 激活方法
application.yml
```yaml
spring:
    profiles:
        active: dev
```
> 那么会激活application-dev.yml中的配置
生效的key-value配置为application.yml 和application-dev.yml 中的配置。
有相同的key时 application-dev.yml中配置生效
默认profile是default
# 常用的激活方法：
### 配置参数
```properties
spring.profiles.active=uat
```
### 命令行
```bash
java -jar XXXX.jar --spring.profiles.active=XXX
```
### jvm参数
```bash
java -jar XXXX.jar -DSpring.profiles.active=XXX
```




