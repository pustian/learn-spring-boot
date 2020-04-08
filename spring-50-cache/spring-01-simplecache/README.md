引入spring-boot-starter-cache模块
@EnableCaching 在主启动类上增加
@Cacheable(value = "emp", key = "#id")
在controller还是service都会生效