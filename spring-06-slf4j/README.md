src/main/resources
使用日志配置文件方式
> logback.xml 直接被日志框架识别
> logback-spring.xml 由springboot加载，允许运用profile标签

### Typical usage pattern
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wombat {

  final Logger logger = LoggerFactory.getLogger(Wombat.class);
  Integer t;
  Integer oldT;

  public void setTemperature(Integer temperature) {

    oldT = t;
    t = temperature;

    logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);

    if(temperature.intValue() > 50) {
      logger.info("Temperature has risen above 50 degrees.");
    }
  }
}
```
### Fluent Logging API
SINCE 2.0.0 SLF4J API version 2.0.0 requires Java 8 and introduces a backward-compatible fluent logging API. 
```java
int newT = 15;
int oldT = 16;

// using traditional API
logger.debug("Temperature set to {}. Old temperature was {}.", newT, oldT);

// using fluent API, add arguments one by one and then log message
logger.atDebug().addArgument(newT).addArgument(oldT).log("Temperature set to {}. Old temperature was {}.");

// using fluent API, log message with arguments
logger.atDebug().log("Temperature set to {}. Old temperature was {}.", newT, oldT);

// using fluent API, add one argument and then log message providing one more argument
logger.atDebug().addArgument(newT).log("Temperature set to {}. Old temperature was {}.", oldT);

// using fluent API, add one argument with a Supplier and then log message with one more argument.
// Assume the method t16() returns 16.
logger.atDebug().addArgument(() -> t16()).log(msg, "Temperature set to {}. Old temperature was {}.", oldT);
```