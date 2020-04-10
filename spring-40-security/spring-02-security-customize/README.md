https://docs.spring.io/spring-security/site/docs/

### 安全框架基本概念
+ “认证” （Authentication）：身份认证/登录，
        验证用户是不是拥有相应的身份；
+ “授权”（Authorization） ：授权，即权限验证，
        验证某个已认证的用户是否拥有某个权限；
        即判断用户是否能做事情，常见的如：验证某个用户是否拥有某个角色。
        一般通过角色控制权限
### 引入Maven依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 几个重要类
+ WebSecurityConfigurerAdapter：
    自定义Security策略，配置类需要继承这个类，实现其两个configure方法（认证和授权）
+ AuthenticationManagerBuilder：
    自定义认证策略
+ HttpSecurity：
    自定义授权策略
+ @EnableWebSecurity：
    开启WebSecurity模式

### 配置环境
##### TestController.java
```java
public class TestController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("level1/1")
    public String one() {
        return "level1/1";
    }

    @GetMapping("level2/2")
    public String two() {
        return "level2/2";
    }

    @GetMapping("level3/3")
    public String three() {
        return "level3/3";
    }
}
```
##### 并同样的 thymeleaf 网页
```bash
>src/main/resources/
 ├── application.yml
 ├── static
 └── templates
     ├── index.html
     ├── level1
     │   └── 1.html
     ├── level2
     │   └── 2.html
     └── level3
         └── 3.html
```
##### 安全配置类 WebSecurityConfig
```java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/webjars/**", "/static/**").permitAll()
                     /*level1路径下需要VIP1身份才能访问*/
                     .antMatchers("/level1/**").hasRole("VIP1")
                     /*level1路径下需要VIP2身份才能访问*/
                     .antMatchers("/level2/**").hasRole("VIP2")
                     /*level1路径下需要VIP3身份才能访问*/
                     .antMatchers("/level3/**").hasRole("VIP3");
         /* 开启自动配置的登录功能，如果没有登录就会来到登录页面
            1.formLogin 会自动生成登录页面 /login
            2.假如登录失败会重定向到login?error
        */
        http.formLogin();
        
        /*开启注销功能,访问/logout并清空session。 默认注销成功后会返回 login?logout */
        http.logout()
            /*重定向注销成功后返回页面*/
            .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*从内存中读取认证*/
        auth.inMemoryAuthentication()
            /*Spring Security 5.0开始必须要设置加密方式 */
            .passwordEncoder(new BCryptPasswordEncoder())
            .withUser("user1").password(new BCryptPasswordEncoder().encode("123")).roles("VIP1", "VIP2")
            .and()
            .withUser("user2").password(new BCryptPasswordEncoder().encode("123")).roles("VIP2", "VIP3")
            .and()
            .withUser("user3").password(new BCryptPasswordEncoder().encode("123")).roles(new String[]{"VIP3", "VIP1"});
    }
}
```
>+ protected void configure(AuthenticationManagerBuilder auth) 
>
>  + 内存中读取认证。 注意此处需要加密方式
>
>  ```java
>  // 此处支持两种写法
>  roles("VIP1","VIP2") 
>  roles(new String[]{"VIP3", "VIP1"});
>  ```
>
>+ protected void configure(HttpSecurity http)
>
>  + 授权 
>  + 匹配用户和角色授权的
>
>  ```java
>          http.authorizeRequests().antMatchers("/", "/webjars/**", "/static/**").permitAll()
>                       /*level1路径下需要VIP1身份才能访问*/
>                       .antMatchers("/level1/**").hasRole("VIP1")
>                       /*level1路径下需要VIP2身份才能访问*/
>                       .antMatchers("/level2/**").hasRole("VIP2")
>                       /*level1路径下需要VIP3身份才能访问*/
>                       .antMatchers("/level3/**").hasRole("VIP3");
>  ```
>
>  + 开启自动配置的登录功能。 此处使用的是自动跳转功能
>
>  ```java
>         /* 开启自动配置的登录功能，如果没有登录就会来到登录页面
>              1.formLogin 会自动生成登录页面 /login
>              2.假如登录失败会重定向到login?error
>          */
>          http.formLogin();
>  ```
>
>  + 开启注销功能
>
>  ```java
>          /*开启注销功能,访问/logout并清空session。 默认注销成功后会返回 login?logout */
>          http.logout()
>              /*重定向注销成功后返回页面*/
>              .logoutSuccessUrl("/");
>  ```
>
>  + 开启记住我的功能
>
>  ```java
>          /*开启记住我功能，登录会添加Cookie,点击注销会删除Cookie*/
>          http.rememberMe();
>  ```
>
>  
>

### 测试

浏览器直接访问 http://localhost:8080/level1/1  会跳转到登陆页面。

此处登陆页面使用的为 maven库中带有的的页面，不用专门写登陆页面。

会跳转到 http://localhost:8080/login 先要登陆，才能根据权限跳转



在index.html中增加logout

```html
    <form th:action="@{/logout}" method="post">
        <input type="submit" value="注销">
    </form>
```

开启注销功能后，再分别测试注销后url地址和 重定向



### thymeleaf 安全属性根据角色显示

pom.xml 中增加

```xml
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        </dependency>
```

html中使用见 index.html 的一些标签



rememberMe 功能。会生成cookies



## 定制化

### 定制化登陆页

```java
        /* 开启自动配置的登录功能，如果没有登录就会来到登录页面
            1.formLogin 会自动生成登录页面 /login
            2.假如登录失败会重定向到login?error
        */
        http.formLogin()
            /*
                定制自己的登录界面
                默认username字段提交用户名，可以通过usernameParameter自定义
                默认password字段提交密码，可以用过passwordParameter自定义
                定制了登录页面后
                登录页面地址的POST请求就是登录请求，可以用过loginProcessingUrl自定义
              */
            .loginPage("/userlogin");
```

get请求 登陆页面 也就是应该可以查看的 index.html 发送的地址

post请求 登陆也登陆的 form中的地址

代码

index.html

```html
    <h2 align="center">游客你好<a th:href="@{/userlogin}">请登录</a></h2>
```

TestController.java

```java
    @GetMapping("/userlogin")
    public String login() {
        return "user-login";
    }
```

user-login.html

```html
<!DOCTYPE html>
<html lang="en"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
</head>
<body>
<h1 align="center">用户安全登录系统</h1>

<hr>
<div align="center">
    <form th:action="@{/userlogin}" method="post">
        用户名：<input name="username"/><br>
        密码：<input name="password"/><br>
        <input type="checkbox" name="remember">记住我<br/>
        <input type="submit" value="登录">
    </form>
</div>
</body>
</html>
```

默认地址的为 /login 

### 默认规则

默认使用的post和get使用的时同一个地址和 username,password remember的参数



### 权限异常

对应的异常处理类

```java
        // 正常情况下此页面不可见的
        http.exceptionHandling()
            /*设置403错误跳转页面*/
            .accessDeniedHandler(new CustomAccessDeniedHandler());
```



