https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/actuator-api/html/
https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-features.html#production-ready-endpoints

### pom.xml
```xml
      <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

### 验证
```bash
dew@diyu204:~$ curl http://192.168.1.204:8080/actuator
{"_links":{"self":{"href":"http://192.168.1.204:8080/actuator","templated":false},"health":{"href":"http://192.168.1.204:8080/actuator/health","templated":false},"health-path":{"href":"http://192.168.1.204:8080/actuator/health/{*path}","templated":true},"info":{"href":"http://192.168.1.204:8080/actuator/info","templated":false}}}
```
返回值格式化后
```json
{
	"_links": {
		"self": {
			"href": "http://192.168.1.204:8080/actuator",
			"templated": false
		},
		"health": {
			"href": "http://192.168.1.204:8080/actuator/health",
			"templated": false
		},
		"health-path": {
			"href": "http://192.168.1.204:8080/actuator/health/{*path}",
			"templated": true
		},
		"info": {
			"href": "http://192.168.1.204:8080/actuator/info",
			"templated": false
		}
	}
}
```
### 健康检查
```bash
dew@diyu204:~$ curl http://192.168.1.204:8080/actuator/health
{"status":"UP"}
```
> 状态将是UP只要应用是健康的。如果应用不健康将会显示DOWN,比如与仪表盘的连接异常或者缺水磁盘空间等。

### 源码
org.springframework.boot.actuate.health.HealthEndpoint

## 开启所有endpoint
### application.yml
```yaml
management:
    endpoints:
        web:
            exposure:
                include: "*"
```
### 再次验证
```bash
dew@diyu204:~$ curl http://192.168.1.204:8080/actuator
{"_links":{"self":{"href":"http://192.168.1.204:8080/actuator","templated":false},"beans":{"href":"http://192.168.1.204:8080/actuator/beans","templated":false},"caches-cache":{"href":"http://192.168.1.204:8080/actuator/caches/{cache}","templated":true},"caches":{"href":"http://192.168.1.204:8080/actuator/caches","templated":false},"health":{"href":"http://192.168.1.204:8080/actuator/health","templated":false},"health-path":{"href":"http://192.168.1.204:8080/actuator/health/{*path}","templated":true},"info":{"href":"http://192.168.1.204:8080/actuator/info","templated":false},"conditions":{"href":"http://192.168.1.204:8080/actuator/conditions","templated":false},"configprops":{"href":"http://192.168.1.204:8080/actuator/configprops","templated":false},"env-toMatch":{"href":"http://192.168.1.204:8080/actuator/env/{toMatch}","templated":true},"env":{"href":"http://192.168.1.204:8080/actuator/env","templated":false},"loggers":{"href":"http://192.168.1.204:8080/actuator/loggers","templated":false},"loggers-name":{"href":"http://192.168.1.204:8080/actuator/loggers/{name}","templated":true},"heapdump":{"href":"http://192.168.1.204:8080/actuator/heapdump","templated":false},"threaddump":{"href":"http://192.168.1.204:8080/actuator/threaddump","templated":false},"metrics":{"href":"http://192.168.1.204:8080/actuator/metrics","templated":false},"metrics-requiredMetricName":{"href":"http://192.168.1.204:8080/actuator/metrics/{requiredMetricName}","templated":true},"scheduledtasks":{"href":"http://192.168.1.204:8080/actuator/scheduledtasks","templated":false},"mappings":{"href":"http://192.168.1.204:8080/actuator/mappings","templated":false}}}
```
```json
{
	"_links": {
		"self": {
			"href": "http://192.168.1.204:8080/actuator",
			"templated": false
		},
		"beans": {
			"href": "http://192.168.1.204:8080/actuator/beans",
			"templated": false
		},
		"caches-cache": {
			"href": "http://192.168.1.204:8080/actuator/caches/{cache}",
			"templated": true
		},
		"caches": {
			"href": "http://192.168.1.204:8080/actuator/caches",
			"templated": false
		},
		"health": {
			"href": "http://192.168.1.204:8080/actuator/health",
			"templated": false
		},
		"health-path": {
			"href": "http://192.168.1.204:8080/actuator/health/{*path}",
			"templated": true
		},
		"info": {
			"href": "http://192.168.1.204:8080/actuator/info",
			"templated": false
		},
		"conditions": {
			"href": "http://192.168.1.204:8080/actuator/conditions",
			"templated": false
		},
		"configprops": {
			"href": "http://192.168.1.204:8080/actuator/configprops",
			"templated": false
		},
		"env-toMatch": {
			"href": "http://192.168.1.204:8080/actuator/env/{toMatch}",
			"templated": true
		},
		"env": {
			"href": "http://192.168.1.204:8080/actuator/env",
			"templated": false
		},
		"loggers": {
			"href": "http://192.168.1.204:8080/actuator/loggers",
			"templated": false
		},
		"loggers-name": {
			"href": "http://192.168.1.204:8080/actuator/loggers/{name}",
			"templated": true
		},
		"heapdump": {
			"href": "http://192.168.1.204:8080/actuator/heapdump",
			"templated": false
		},
		"threaddump": {
			"href": "http://192.168.1.204:8080/actuator/threaddump",
			"templated": false
		},
		"metrics": {
			"href": "http://192.168.1.204:8080/actuator/metrics",
			"templated": false
		},
		"metrics-requiredMetricName": {
			"href": "http://192.168.1.204:8080/actuator/metrics/{requiredMetricName}",
			"templated": true
		},
		"scheduledtasks": {
			"href": "http://192.168.1.204:8080/actuator/scheduledtasks",
			"templated": false
		},
		"mappings": {
			"href": "http://192.168.1.204:8080/actuator/mappings",
			"templated": false
		}
	}
}
```
> 可以看到所有已经启动的节点

### 监控示例
```metrics
http://localhost:8080/actuator/metrics
# 具体的值
http://localhost:8080/actuator/metrics/http.server.requests
根据tag进行筛选
http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/actuator/metrics
多个tag来进行筛选，中间用,隔开就行了
http://localhost:8080/actuator/metrics/http.server.requests?tag=uri:/actuator/metrics,status:500
```
+ 服务重启后，上面的值都会归零
可以按照固定的频率重复请求actuator暴露的endpoint,并将每次请求的结果保存到时序数据库中（例如influxDB，或OpenTSDB），再用grafana来展示出来，是比较常用的监控套路。

```properties
management:
  endpoint:
    health:
        show-details: always
    shutdown:
        enabled: true
```
```bash
curl http://localhost:8080/actuator/health
curl 'http://localhost:8080/actuator/shutdown' -i -X POST
```

##自定义的健康指标
```java
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // Use the builder to build the health status details that should be reported.
        // If you throw an exception, the status will be DOWN with the exception message.
        builder.up()
                .withDetail("app", "Alive and Kicking")
                .withDetail("error", "Nothing! I'm good.");
    }
}

```
http://localhost:8080/actuator/health
```json
{
	"status": "UP",
	"components": {
		"custom": {
			"status": "UP",
			"details": {
				"app": "Alive and Kicking",
				"error": "Nothing! I'm good."
			}
		},
		"diskSpace": {
			"status": "UP",
			"details": {
				"total": 1967397240832,
				"free": 943721512960,
				"threshold": 10485760
			}
		},
		"ping": {
			"status": "UP"
		}
	}
}
```



需要注意security中设置


