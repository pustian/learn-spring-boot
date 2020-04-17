# dev0
# 原型
    https://www.cnblogs.com/c1024/p/11011997.html

测试
```bash
dew@diyu204:~/workshop/github.com$ curl http://localhost:8080/hello
Hello World
dew@diyu204:~/workshop/github.com$ curl http://localhost:8080/api/hello
{"error":"unauthorized","error_description":"Full authentication is required to access this resource"}
```
获取token
```bash
dew@diyu204:~/workshop/github.com$ curl -i -X POST -d "username=admin&password=123456&grant_type=password&client_id=client&client_secret=secret" http://localhost:8080/oauth/token
HTTP/1.1 200 
Cache-Control: no-store
Pragma: no-cache
X-Content-Type-Options: nosniff
X-XSS-Protection: 1; mode=block
X-Frame-Options: DENY
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Fri, 17 Apr 2020 05:24:25 GMT

{"access_token":"6e7df2c2-8593-4aa9-aa71-22788b8e4f5d","token_type":"bearer","refresh_token":"4e9bb35b-c946-43be-9196-7ca582242ab1","expires_in":1199,"scope":"all"}dew@diyu204:~/workshop/github.com$
```
带token访问
```bash
dew@diyu204:~/workshop/github.com$ curl -H "Authorization:Bearer 6e7df2c2-8593-4aa9-aa71-22788b8e4f5d"  http://localhost:8080/api/hello
Hello World API
dew@diyu204:~/workshop/github.com$
```    
获取token时 
```
Q:
{"error":"invalid_grant","error_description":"用户名或密码错误"}
A:
是由于UserDetailService中用户名 密码导致的
```
```
Q:
{"error":"invalid_request","error_description":"Missing grant type"}
A:
是由于 client_secret 错误引起的
```

WebSecurityConfigurerAdapter与ResourceServerConfigurerAdapter
二者都有针对http security的配置，他们的默认配置如下
WebSecurityConfigurerAdapter
spring-security-config-4.2.3.RELEASE-sources.jar!/org/springframework/security/config/annotation/web/configuration/WebSecurityConfigurerAdapter.java
```java
@Order(100)
public abstract class WebSecurityConfigurerAdapter implements
		WebSecurityConfigurer<WebSecurity> {
		//......
protected void configure(HttpSecurity http) throws Exception {
		logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");

		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin().and()
			.httpBasic();
	}

	//......
}	
```
可以看到WebSecurityConfigurerAdapter的order是100

spring-security-oauth2-2.0.14.RELEASE-sources.jar!/org/springframework/security/oauth2/config/annotation/web/configuration/ResourceServerConfigurerAdapter.java
ResourceServerConfigurerAdapter
```
public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated();
	}
```
它的order是SecurityProperties.ACCESS_OVERRIDE_ORDER - 1
spring-boot-autoconfigure-1.5.5.RELEASE-sources.jar!/org/springframework/boot/autoconfigure/security/oauth2/resource/ResourceServerProperties.java
```java
/**
	 * The order of the filter chain used to authenticate tokens. Default puts it after
	 * the actuator endpoints and before the default HTTP basic filter chain (catchall).
	 */
	private int filterOrder = SecurityProperties.ACCESS_OVERRIDE_ORDER - 1;
```
由此可见WebSecurityConfigurerAdapter 的拦截要优先于ResourceServerConfigurerAdapter

二者关系
WebSecurityConfigurerAdapter用于保护oauth相关的endpoints，同时主要作用于用户的登录(form login,Basic auth)
ResourceServerConfigurerAdapter用于保护oauth要开放的资源，同时主要作用于client端以及token的认证(Bearer auth)

因此二者是分工协作的
在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
```java
@Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatchers().antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated();
    }
```
在ResourceServerConfigurerAdapter配置需要token验证的资源
```java
@Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated();
    }
```

dev1
dev0 基本类似UserDetailService 使用了用户名
roles 也增加了
测试如下
```bash
dew@diyu204:~$ curl http://localhost:8080/test
test
```
```bash
dew@diyu204:~$ curl http://localhost:8080/admins
{"timestamp":"2020-04-17T10:56:31.873+0000","status":500,"error":"Internal Server Error","message":"An Authentication object was not found in the SecurityContext","path":"/admins"}
```
```bash
dew@diyu204:~$ curl http://localhost:8080/admins/id
admin/id
```
原因 ResourceServer.java
```java
        /*admins路径下需要ADMIN身份才能访问*/
        httpSecurity.authorizeRequests().antMatchers("/admins/**")
                .hasAnyRole("ADMIN").anyRequest().authenticated();
```
                
```bash
dew@diyu204:~$ curl http://localhost:8080/users
{"error":"unauthorized","error_description":"Full authentication is required to access this resource"} 
```
```bash
dew@diyu204:~$ curl http://localhost:8080/users/id
{"error":"unauthorized","error_description":"Full authentication is required to access this resource"} 
dew@diyu204:~$ 
```
原因 ResourceServer.java
```java
        httpSecurity.requestMatchers().antMatchers("/users/**")
                .and().authorizeRequests().antMatchers("/users/**").authenticated();
```
admin
```bash
curl -i -X POST -d "client_id=client-id&client_secret=authorize-secret&grant_type=password&scopes=all&username=admin&password=123456" http://localhost:8080/oauth/token
{
    "access_token": "56480625-9b41-4022-bc79-741c22e9b581",
    "token_type": "bearer",
    "refresh_token": "4997755b-db3c-40d2-b37b-d53e132441d0",
    "expires_in": 3599,
    "scope": "all"
}
```
```bash
dew@diyu204:~$ curl -H "Authorization:Bearer 56480625-9b41-4022-bc79-741c22e9b581"  http://localhost:8080/admins
{"timestamp":"2020-04-17T11:20:05.086+0000","status":500,"error":"Internal Server Error","message":"An Authentication object was not found in the SecurityContext","path":"/admins"} 
```
```bash
dew@diyu204:~$ curl -H "Authorization:Bearer 56480625-9b41-4022-bc79-741c22e9b581"  http://localhost:8080/admins/1
admin/1
```
```bash
dew@diyu204:~$ curl -H "Authorization:Bearer 56480625-9b41-4022-bc79-741c22e9b581"  http://localhost:8080/users/1
users/1
```
```bash
dew@diyu204:~$ curl -H "Authorization:Bearer 56480625-9b41-4022-bc79-741c22e9b581"  http://localhost:8080/users
{"error":"access_denied","error_description":"不允许访问"}dew@diyu204:~$
```

user
```
curl -i -X POST -d "client_id=client-id&client_secret=authorize-secret&grant_type=password&scopes=all&username=user&password=123456" http://localhost:8080/oauth/token
{
    "access_token": "6c857c17-ff83-4990-ae55-aad690a6815e",
    "token_type": "bearer",
    "refresh_token": "a3c0f75e-d0f1-43d9-b622-0e512d508c26",
    "expires_in": 3600,
    "scope": "all"
}
```
```bash
dew@diyu204:~$ curl -H "Authorization:Bearer 6c857c17-ff83-4990-ae55-aad690a6815e"  http://localhost:8080/users
["张三","李四","王五"]
dew@diyu204:~$ curl -H "Authorization:Bearer 6c857c17-ff83-4990-ae55-aad690a6815e"  http://localhost:8080/admins
{"timestamp":"2020-04-17T11:22:40.227+0000","status":500,"error":"Internal Server Error","message":"An Authentication object was not found in the SecurityContext","path":"/admins"}
dew@diyu204:~$ curl -H "Authorization:Bearer 6c857c17-ff83-4990-ae55-aad690a6815e"  http://localhost:8080/admins/1
admin/1
dew@diyu204:~$ curl -H "Authorization:Bearer 6c857c17-ff83-4990-ae55-aad690a6815e"  http://localhost:8080/users/1
users/1dew@diyu204:~$ 
```

鉴权在此处还有写问题需要解决
