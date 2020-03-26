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

### spring-04-profiles

### spring-01-logback

### spring-02-swagger-ui

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



## Spring启动步骤