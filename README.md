# learn-spring-boot

> jdk1.8
>
> mvn 3.5.X
>
> spring-boot 2.X

### maven setting.xml 

```xml
<!-- 加快下载 --> 
<mirrors>
    <mirror>
     <id>alimaven</id>
     <name>aliyun maven</name>
     <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
     <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
<!--版本 --> 
       <profile>   
            <id>jdk1.8</id>    
            <activation>   
                <activeByDefault>true</activeByDefault>    
                <jdk>1.8</jdk>   
            </activation>    
            <properties>   
                <maven.compiler.source>1.8</maven.compiler.source>    
                <maven.compiler.target>1.8</maven.compiler.target>    
                <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>   
            </properties>   
        </profile>

```



https://docs.spring.io/spring-boot/docs/2.1.13.RELEASE/reference/html/

### spring-01
+ SpringBootApplication注解
+ SpringApplication启动项
+ 修改port
+ 修改banner
+ 控制banner
+ Test

### spring-02-properties

+ yaml
+ properties
+ value

### spring-03-annotation01

+ PropertySource 引入自定义的properties
+ ImportResource 引入自定义的spring-mvn xml配置。

### spring-03-annotation02

+ SpringBootApplication 注解说明
+ 配置注解 *AutoConfiguration

### spring-04-profiles

+ 多环境properties/yml
+ 代码中 @profile 配置环境
+ 激活方法 
  + 命令行
    + jvm 参数 -DSpring.profiles.active=
    + 命令行 --spring.profiles.active=
  + 配置 application.yml

### spring-05-config

+ 加载配置文件 file:
  + file:./config
  + file:./
  + classpath:./config/
  + classpath:/
+ 命令行
  + --spring.config.location=

### spring-06-slf4j

+ src/main/resources
  + logback-spring.xml
+ 日志原理参看原理
+ 也可以指定参考spring-05-config

### spring-10-web

+ webjars  引用

+ 资源访问

+ #### favicon.ico

### spring-10-web-thymeleaf

+ 支持多个web模板。推荐thymeleaf 模板引擎

### spring-10-web-thymeleaf-i18

+ 国际化 注意编码

### spring-10-web-thymeleaf-crud

+ thymeleaf 示例







### spring-10-mybatis

+ pom.xml 增加jdbc mybatis connector配置
+ @mapperscan(xxx.xxx.mapper)
+ application.yaml 增加spring.datasource.
+ entity.XXXX 实体类
+ mapper.XXXXMapper 接口
+ mapper/XXXXmapper.xml 配置文件

### Spring-10-mybatis2

+ pom.xml 增加jdbc mybatis connector配置
+ @mapperscan(xxx.xxx.mapper)
+ application.yaml 增加spring.datasource.
+ entity.XXXX 实体类
+ mapper.XXXXMapper 接口

### Spring-10-mybatis-durid

### Spring-10-jpa

### Spring-10-jdbc

### Spring-30-kafka

+ kafka-01 producer,consumer 示例代码
+ kafka-02 自定义解析器目前 --- 有问题









## Spring启动步骤