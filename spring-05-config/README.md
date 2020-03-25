spring boot启动会扫描以下位置的application.properties/application.yml 作为springboot的默认配置文件
```yaml
- file:./config
- file:./
- classpath:./config/
- classpath:/
```
先加载./config 最后加载 classpath:/
查看日志
```properties
2020-03-25 23:37:23.549 DEBUG 2208 --- [           main] o.s.b.c.c.ConfigFileApplicationListener  : Loaded config file 'file:./config/application.yml' (file:./config/application.yml)
2020-03-25 23:37:23.549 DEBUG 2208 --- [           main] o.s.b.c.c.ConfigFileApplicationListener  : Loaded config file 'file:./application.yml' (file:./application.yml)
2020-03-25 23:37:23.549 DEBUG 2208 --- [           main] o.s.b.c.c.ConfigFileApplicationListener  : Loaded config file 'file:/D:/workshop/personal/github.com/pustian/learn-spring-boot/spring-05-config/target/classes/config/application.yml' (classpath:/config/application.yml)
2020-03-25 23:37:23.549 DEBUG 2208 --- [           main] o.s.b.c.c.ConfigFileApplicationListener  : Loaded config file 'file:/D:/workshop/personal/github.com/pustian/learn-spring-boot/spring-05-config/target/classes/application.yml' (classpath:/application.yml)
```
都加载一边，互补配置

spring.config.location 来共同作用
```bash
java -jar  XXX.jar
```
Q：
```
为什么命令行时 debug:true 不生效。
```
