pom.xml
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```
application.yml
```xml
spring:
    datasource:
        url: jdbc:mysql://192.168.1.205:3306/subway?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
        username: root
        password: diyu654321
        driver-class-name: com.mysql.jdbc.Driver
```
自动配置原理
DataSourceConfiguration 支持的数据源类型

+ ```
  spring.datasource.tomcat
  ```

+ ```
  spring.datasource.hikari
  ```

+ ```
  spring.datasource.dbcp2
  ```

+ 自定义

默认使用的是tomcat配置

```prope
# Number of ms to wait before throwing an exception if no connection is available.
spring.datasource.tomcat.max-wait=10000

# Maximum number of active connections that can be allocated from this pool at the same time.
spring.datasource.tomcat.max-active=50

# Validate the connection before borrowing it from the pool.
spring.datasource.tomcat.test-on-borrow=true
```



# FQA

Q: 数据库里时间不对

>  有时间段偏移

A:

```
url: jdbc:mysql://192.168.1.106:3306/data_dictionary?serverTimezone=Asia/Shanghai
中需要有时区
?serverTimezone=Asia/Shanghai
```



DataSourceInitializer 2.x 以上版本需要调整 ？？？

+ ```
  spring.datasource.schema
  运行schema-xxx.sql 建表语句
  数据语句 data-xxx.sql
  ```
  
  
  
  