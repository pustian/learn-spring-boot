引入spring-boot-starter-cache模块
@EnableCaching 在主启动类上增加
@Cacheable(value = "emp", key = "#id")
在controller还是service都会生效

增加pom.xml
> 未试过组合 net.sf.ehcache 和 javax.cache
```xml
        <!-- jcache 坐标 -->
        <dependency>
            <groupId>org.ehcache</groupId>
            <artifactId>ehcache</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.cache</groupId>
            <artifactId>cache-api</artifactId>
        </dependency>
```
ehcache/ehcache.xml 与net.sf.ehcache时已不一样了
```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache:config
        xmlns:ehcache="http://www.ehcache.org/v3"
        xmlns:jcache="http://www.ehcache.org/v3/jsr107">

    <ehcache:service>
        <jcache:defaults>
            <jcache:cache name="default" template="myDefaultTemplate"/>
        </jcache:defaults>
    </ehcache:service>

    <ehcache:cache alias="allCameraCache">
        <ehcache:key-type copier="org.ehcache.impl.copy.SerializingCopier">java.lang.String</ehcache:key-type>
        <ehcache:value-type copier="org.ehcache.impl.copy.SerializingCopier">java.lang.String</ehcache:value-type>
        <ehcache:expiry>
            <ehcache:tti unit="minutes">20</ehcache:tti><!-- 数据过期时间20分钟 -->
        </ehcache:expiry>
        <ehcache:heap unit="entries">200</ehcache:heap><!-- 最多缓存200个对象 -->
    </ehcache:cache>

    <!-- 使用模板，可以覆盖模板的属性 -->
    <ehcache:cache alias="emp" uses-template="myDefaultTemplate">
        <ehcache:key-type>java.lang.Object</ehcache:key-type>
        <ehcache:value-type>java.lang.Object</ehcache:value-type>
        <ehcache:expiry>
            <ehcache:tti unit="minutes">30</ehcache:tti><!-- 数据过期时间30分钟，覆盖模板默认属性 -->
        </ehcache:expiry>
        <ehcache:heap unit="entries">500</ehcache:heap><!-- 最多缓存500个对象 -->
    </ehcache:cache>

    <!-- 默认模板 -->
    <ehcache:cache-template name="myDefaultTemplate">
        <ehcache:expiry>
            <ehcache:none/><!-- 缓存永不过期 -->
        </ehcache:expiry>
    </ehcache:cache-template>

</ehcache:config>
```
> ehcache:cache alias="emp"  需要 Cacheable 中的value 匹配

application.yml
```yaml
spring:
    cache:
        jcache:
            config: classpath:ehcache/ehcache.xml
```
