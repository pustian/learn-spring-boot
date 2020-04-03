# mybatis + mysql

http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/

### 结构如下
```bash
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── tian
    │   │       └── pusen
    │   │           ├── Application.java
    │   │           ├── entity
    │   │           │   └── Test.java
    │   │           ├── mapper
    │   │           │   └── TestMapper.java
    │   │           └── service
    │   │               ├── impl
    │   │               │   └── TestServiceImpl.java
    │   │               └── ITestService.java
    │   └── resources
    │       ├── application-dev.yml
    │       ├── application.yml
    │       └── mapper
    │           └── TestMapper.xml
    └── test
        └── java

```

### pom.xml
> dependencies 中增加
```xml
    <dependencies>
        <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-data-jdbc</artifactId>
            </dependency>
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>2.1.2</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <scope>runtime</scope>
            </dependency>
    </dependencies>
```
### main 增加扫描 注解
```java
@MapperScan("tian.pusen.mapper")
```
### 建表sql
```sql
create table `test`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `gmt_create` datetime DEFAULT NULL,
    `gmt_modified` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试表';
```

### 增加实体类
```java
public class Test implements Serializable {
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;
    // set get ...
}
```
### 增加Mapper接口
```java
// @Mapper
public interface TestMapper {
    Integer insertRecord(Test test);
    Test getById(Long id);
    List<Test> getAll();
}
```
### 增加接口配置mapper.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="tian.pusen.mapper.TestMapper">

    <resultMap id="BaseResultMap" type="tian.pusen.entity.Test">
        <result column="id" jdbcType="INTEGER" property="id" />
        <result column="gmt_create" jdbcType="TIMESTAMP" property="gmtCreate" />
        <result column="gmt_modified" jdbcType="TIMESTAMP" property="gmtModified" />

    </resultMap>

    <insert id="insertRecord" parameterType="tian.pusen.entity.Test" >
        insert into test(id, gmt_create, gmt_modified) values(#{id}, #{gmtCreate}, #{gmtModified});
    </insert>

    <select id="getById" resultType="tian.pusen.entity.Test" parameterType="java.lang.Long">
        select id, gmt_create, gmt_modified from test where id = #{id};
    </select>

    <select id="getAll" resultMap="BaseResultMap">
        select id, gmt_create, gmt_modified from test;
    </select>

</mapper>
```
### application.yml 中增加jdbc配置
```yaml
spring:
    datasource:
        username: root
        password: diyu654321
        url: jdbc:mysql://192.168.1.205:3306/subway?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
        driver-class-name: com.mysql.jdbc.Driver

mybatis:
    mapper-locations: classpath:mapper/*Mapper.xml
    type-aliases-package: tian.pusen.entity

#showSql
logging:
    level:
        tian:
            pusen:
                mapper : debug
```

# 其他

>  @Mapper，最终 Mybatis 会有一个拦截器，会自动的把 @Mapper 注解的接口生成动态代理类。@Mapper 注解针对的是一个一个的类，相当于是一个一个 Mapper.xml 文件。而一个接口一个接口的使用 @Mapper，太麻烦了，于是 @MapperScan 就应用而生了。
>
> @MapperScan 配置一个或多个包路径，自动的扫描这些包路径下的类，自动的为它们生成代理类。

所以使用@MapperScan的话，不用在使用Mapper注解