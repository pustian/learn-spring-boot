引入spring-boot-starter-cache模块
引入spring-boot-starter-data-redis模块
@EnableCaching 在主启动类上增加
@Cacheable(value = "emp", key = "#id")
在controller还是service都会生效

spring boot框架中已经集成了redis，
在1.x.x的版本时默认使用的jedis客户端，
现在是2.x.x版本默认使用的lettuce客户端
依赖包
```xml
         <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-data-redis</artifactId>
         </dependency>
        <!-- redis依赖commons-pool 这个依赖一定要添加 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
```

可以直接使用redisTemplate 作客户端操作 见 ApplicationTest.java

如果有需要的花需要自己写RedisConfig
