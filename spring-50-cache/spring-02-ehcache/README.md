引入spring-boot-starter-cache模块
@EnableCaching 在主启动类上增加
@Cacheable(value = "emp", key = "#id")
在controller还是service都会生效

增加pom.xml
```xml
        <!-- Ehcache 坐标 -->
        <dependency>
            <groupId>net.sf.ehcache</groupId>
            <artifactId>ehcache</artifactId>
        </dependency>
```
ehcache/ehcache.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache  name="learncache" updateCheck="false">

    <!-- 磁盘缓存位置 -->
    <diskStore path="java.io.tmpdir"/>

    <defaultCache
            maxElementsInMemory="10000"
            eternal="false"
            timeToIdleSeconds="120"
            timeToLiveSeconds="120"
            maxElementsOnDisk="10000000"
            diskExpiryThreadIntervalSeconds="120"
            memoryStoreEvictionPolicy="LRU">
        <persistence strategy="localTempSwap"/>
    </defaultCache>
    <cache name="emp"
           maxElementsInMemory="10000"
           eternal="false"
           timeToIdleSeconds="120"
           timeToLiveSeconds="120"
           maxElementsOnDisk="10000000"
           diskExpiryThreadIntervalSeconds="120"
           memoryStoreEvictionPolicy="LRU">
        <persistence strategy="localTempSwap"/>
    </cache>
</ehcache>
```
> cache name 需要 Cacheable 中的value 匹配

application.yml
```yaml
spring:
    cache:
        ehcache:
            config: classpath:ehcache/ehcache.xml
```
