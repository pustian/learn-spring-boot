# mybatis + mysql
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
    │       ├── application-dev2.yml
    │       └── application.yml
    └── test
        └── java

```
### mybatis-annotation 
https://www.tutorialspoint.com/mybatis/mybatis_annotations.htm
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
@Mapper
public interface TestMapper {
    @Insert("insert into test values(#{id}, #{gmtCreate}, #{gmtModified})")
    Integer insertRecord(Test test);
    
    @Select("select id, gmt_create, gmt_modified from test where id = ${id}")
    Test getById(Long id);
    
    @Select("select id, gmt_create, gmt_modified from test ")
    List<Test> getAll();
}
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
    configuration:
    # 解决数据库建表字段和java字段驼峰写法的一直性映射
        map-underscore-to-camel-case: true
    mapper-locations: classpath:mapper/*Mapper.xml
    type-aliases-package: tian.pusen.entity
#showSql
logging:
    level:
        tian:
            pusen:
                mapper : debug
```
