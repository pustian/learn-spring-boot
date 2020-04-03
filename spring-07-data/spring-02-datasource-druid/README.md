Durid 配置

早期的配置已经不可用了

参考https://github.com/alibaba/druid

# pom.xml
```xml
        <!-- Druid配置 -->
        <!-- https://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.21</version>
        </dependency>
```
# application.yaml
```yaml
spring:
    datasource:
        url: jdbc:mysql://192.168.1.205:3306/subway?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
        username: root
        password: diyu654321
        driver-class-name: com.mysql.jdbc.Driver
        # platform: mysql
        # 这些都是druid 相关的配置
        type: com.alibaba.druid.pool.DruidDataSource
        # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
        filters: stat,wall,slf4j
        druid:
            url: jdbc:mysql://192.168.1.205:3306/subway?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
            username: root
            password: diyu654321
            #        driver-class-name: com.mysql.jdbc.Driver
            driver-class-name: com.mysql.jdbc.Driver
            # 连接池的配置信息
            # 初始化大小，最小等待连接数量，最大等待连接数量，最大连接数
            initialSize: 2
            minIdle: 1
            maxIdle: 5
            maxActive: 20
            # 配置获取连接等待超时的时间
            maxWait: 60000
            # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            timeBetweenEvictionRunsMillis: 60000
            # 配置一个连接在池中最小生存的时间，单位是毫秒
            minEvictableIdleTimeMillis: 300000
            validationQuery: SELECT 1 FROM DUAL
    #        validationQuery: SELECT 'x'
            testWhileIdle: true
            testOnBorrow: false
            testOnReturn: false
            # 打开PSCache，并且指定每个连接上PSCache的大小
            poolPreparedStatements: true
            maxPoolPreparedStatementPerConnectionSize: 20
            maxOpenPreparedStatements: 20
            useGlobalDataSourceStat: true
            # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
            connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
            web-stat-filter:
                enabled: true
                url-pattern: "/*"
                exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
                session-stat-max-count: 1000
                session-stat-enable: true
                profile-enable: true
            stat-view-servlet:
                enabled: true
                url-pattern: "/druid/*"
                login-username: admin
                login-password: admin
                allow: 127.0.0.1,192.168.1.204
                reset-enable: true
```
可以在192.168.1.204 机器上访问 http://192.168.1.204:8080/druid
用户密码见stat-view-servlet中的配置
