# JWT 基础知识
https://jwt.io/introduction/
### 简介
JWT包含了使用 . 分隔的三部分： 
1.Header 头部，包含了两部分：token类型和采用的加密算法。 
2.Payload 负载，Token的第二部分是负载，它包含了claim， Claim是一些实体（通常指的用户）的状态和额外的元数据。 
3.Signature 签名，创建签名需要使用编码后的header和payload以及一个秘钥，使用header中指定签名算法进行签名。 

下面是一个jjwt生成的token, base64 可以解开查看
```bash
eyJhbGciOiJIUzUxMiJ9
.eyJzdWIiOiJkZmRzYSIsImNyZWF0ZWQiOjE0OTQ5MjgzODQ1MzksInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FOT05ZTU9VUyJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9BRE1JTiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn0seyJhdXRob3JpdHkiOiJST0xFX0RCQSJ9XSwiaWQiOjAsImV4cCI6MTQ5NTUzMzE4NH0
.RAWhCcFj7sfXI81zJ8fm0Rfb0IpwT7mNfuFPGzU6AblW2UdOgMtDExXlWZEr3pracdytsfw3os4dnJKM6ZW9mA
对应header和 payload解码后内容
{"alg":"HS512"}
{"sub":"dfdsa","created":1494928384539,"roles":[{"authority":"ROLE_ANONYMOUS"},{"authority":"ROLE_ADMIN"},{"authority":"ROLE_USER"},{"authority":"ROLE_DBA"}],"id":0,"exp":1495533184}
```
### jwt工作流程
![](./images/jwt-workflow.png)

1. 用户携带username和password请登录
2. 服务器验证登录验证，如果验证成功，根据用户的信息和服务器的规则生成JWT Token
3. 服务器将该token返回 
4. 用户得到token，存在localStorage、cookie或其它数据存储形式中。 
5. 以后用户请求服务器时，在请求的header中加入 Authorization: Bearer xxxx(token) 。此处注意token之前有一个7字符长度的“Bearer “，服务器端对此token进行检验，如果合法就解析其中内容，根据其拥有的权限和业务逻辑反回响应结果。 

JJWT
```xml
        <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
```
```java
public final class JJwtTokenUtils {

    public static String generateToken(UserDetails userDetails, Map<String, Object> privateClaims) {
         String username = userDetails.getUsername();
         Long issuedAt = System.currentTimeMillis();
        return generateToken(ISSUER, SUBJECT, username, EXPIRATION, issuedAt,privateClaims);
    }

    public static Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getAudienceFromToken(token);
        return username.equals(user.getUsername()) && isTokenExpired(token) == false
                && ISSUER.equals(getIssuerFromToken(token)) && SUBJECT.equals(getSubjectFromToken(token) )
                ;
    }
}
```
注意此处时间

另外一套jwt
```xml
        <!-- https://mvnrepository.com/artifact/com.auth0/java-jwt -->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>3.10.2</version>
        </dependency>
```
```java
public final class JavaJwtTokenUtils {
    public static String sign(UserDetails userDetails) {
        String username = userDetails.getUsername();
        Long issuedAt = System.currentTimeMillis();
        return sign(ISSUER, SUBJECT, username, EXPIRATION, issuedAt);
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC512(TOKEN_SECRET);
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            User user = (User) userDetails;
            List<String> aud = jwt.getAudience();
            return aud.contains(user.getUsername());
        }catch (AlgorithmMismatchException | SignatureVerificationException
                | TokenExpiredException | InvalidClaimException e) {
            e.printStackTrace();
            return false;
        }
    }
}
```



# Security + JWT

security 的内容继续使用

### JwtUserDetailService

```java
// 对于接口 UserDetailsService 的实现
@Service
public class JwtUserDetailService implements UserDetailsService {
     @Autowired
    IOperatorService operatorService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails result = null;
        Operator operator = operatorService.getByUsername(username);
        try {
            if (operator == null) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            //用户权限
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (StringUtils.isNotBlank(operator.getRoles())) {
                String[] roles = operator.getRoles().split(",");
                for (String role : roles) {
                    if (StringUtils.isNotBlank(role)) {
                        authorities.add(new SimpleGrantedAuthority(role.trim()));
                    }
                }
            }
            result = new JwtUserDetails(operator);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
```

### JwtUserDetails

```java
// 实现 UserDetails， 以前直接使用 Operator 类对象生成
public class JwtUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Collection<SimpleGrantedAuthority> authorities;

    public JwtUserDetails(Operator operator) {
        id = operator.getId();
        username = operator.getUsername();
        password = operator.getPassword();
        authorities = new ArrayList<SimpleGrantedAuthority>();
        if (StringUtils.isNotBlank(operator.getRoles())) {
            String[] roles = operator.getRoles().split(",");
            for (String role : roles) {
                if (StringUtils.isNotBlank(role)) {
                    authorities.add(new SimpleGrantedAuthority(role.trim()));
                }
            }
        }
    }

    ... getter

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
```

### JJwtTokenUtils

```java
// JWT Token 操作，主要时sign和verify
public final class JJwtTokenUtils  {
    private final static String SECRET = "pusen.tian";
    private final static String SUBJECT = "XX-test-XX";
    private final static String ISSUER = "yuxi";
    private final static Long EXPIRATION = 7200L; // 2h = 2* 60 * 60

    private static String sign(String issuer, String subject, String username, Long expiration, Long issuedAt,
                                       Map<String, Object> privateClaims) {
        Map<String, Object> header = new HashMap<>();
        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.ID, UUID.randomUUID());
        claims.put(Claims.ISSUER, issuer);
        claims.put(Claims.SUBJECT, subject);
        claims.put(Claims.AUDIENCE, username);
        claims.put(Claims.EXPIRATION,  new Date(issuedAt + expiration * 1000L) );
        claims.put(Claims.ISSUED_AT, new Date(issuedAt) );
//        System.out.println(claims.get(Claims.EXPIRATION));
        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .addClaims(privateClaims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
    public static String sign(UserDetails userDetails) {
         String username = userDetails.getUsername();
         Long issuedAt = System.currentTimeMillis();
        return sign(ISSUER, SUBJECT, username, EXPIRATION, issuedAt, null);
    }
    public static Boolean validateToken(String token, String username) {
        final String tokenUsername = getAudienceFromToken(token);
        return null != tokenUsername && tokenUsername.equals(username) && isTokenExpired(token) == false
                && ISSUER.equals(getIssuerFromToken(token)) && SUBJECT.equals(getSubjectFromToken(token) )
                ;
    }
    public static Boolean validateToken(String token, UserDetails userDetails) {
        String username =  userDetails.getUsername();
        return validateToken(token, username);
    }
}
```

### WebSecurityConfig

> 注意 @EnableGlobalMethodSecurity 注解和 .antMatchers("/level1/**").hasRole("VIP1") 选择 权限验证的配置方法，对应的Controller可能需要增加注解

```java
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启方法级的权限注解  性设置后控制器层的方法前的@PreAuthorize("hasRole('admin')") 注解才能起效
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    AccessAuthenticationDenied accessAuthenticationDenied;
    @Autowired
    @Qualifier("jwtUserDetailService")
    UserDetailsService userDetailsService;
    @Autowired
    JWTAuthorizationTokenFilter authenticationTokenFilter;

    //主要进行验证的地方
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // userDetail  // 使用BCrypt进行密码的hash
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
    //拦截在这配
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 由于使用的是JWT，我们这里不需要csrf
        http.csrf().disable();
        // 基于token，所以不需要session
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().none();
        //所有用户可以访问获取token的api "/auth/**" 允许匿名访问
        http.authorizeRequests()
            .antMatchers("/getAll", "/auth/login").permitAll()
            .antMatchers("/v2/api-docs", "/swagger-ui.html",
                    "/swagger-resources/configuration/ui",
                    "/swagger-resources",
                    "/swagger-resources/configuration/security").permitAll()
//            /*level1路径下需要VIP1身份才能访问*/
//            .antMatchers("/level1/**").hasRole("VIP1")
//            .antMatchers("/level2/**").hasRole("VIP2")
//            .antMatchers("/level3/**").hasRole("VIP3")
            .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()
            .anyRequest().authenticated();       // 剩下所有的验证都需要验证

        // 这里面主要配置如果没有凭证，可以进行一些操作，
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(accessAuthenticationDenied);

        // 禁用缓存
        http.headers().cacheControl();

        // 是为了方便后面写前后端分离的时候前端过来的第一次验证请求，这样做，会减少这种请求的时间和资源使用。
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
```

JWTAuthorizationTokenFilter

```java
@Component
public class JWTAuthorizationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 从header中获取凭证authToken
        String tokenHeader = request.getHeader(TOKEN_HEADER);
        String username = null;
        String authToken = null;

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            authToken = tokenHeader.substring(7);
            // token 验证
            username = JJwtTokenUtils.getAudienceFromToken(authToken);
            // 如果请求头中有token，则进行解析，并且设置认证信息
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (JJwtTokenUtils.validateToken(authToken, userDetails)) {
                    // 将用户信息存入 authentication，方便后续校验
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
```

### 权限错误处理

#### AccessAuthenticationDenied

```java
@Component
public class AccessAuthenticationDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        // 登陆状态下，权限不足执行该方法
        System.out.println("权限不足：" + e.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = "权限不足";
        printWriter.write(body);
        printWriter.flush();
    }
}
```

#### JWTAuthenticationEntryPoint

```java
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        // 验证为未登陆状态会进入此方法，认证错误
        System.out.println("认证失败：" + e.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = "认证失败";
        printWriter.write(body);
        printWriter.flush();
    }
}
```

# 测试

前端提交的校验时通过在http的header中增加Authorization这段完成的

### 登陆

```bash
curl -H "Content-Type:applicatio-H "Data_Type:msg" -X POST --data '{"username": "user1"}' http://127.0.0.1:8081/auth/login
```

返回token值

```bash
dew@diyu204:~/workshop/github.com/auth$ curl -H "Content-Type:application/json" -H "Data_Type:msg" -X POST --data '{"username": "user1"}' http://127.0.0.1:8081/auth/login
eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJYWC10ZXN0LVhYIiwiYXVkIjoidXNlcjEiLCJpc3MiOiJ5dXhpIiwiZXhwIjoxNTg2NzY3Mjc1OTg0LCJpYXQiOjE1ODY3NjAwNzU5ODQsImp0aSI6IjA4ZmZjZGQ0LWIxNGUtNGU1Mi04OWUwLWZhZjg3NjkxNDAyYSJ9.35rxLpPzK9VrrP_StlQJZHNHe9_60AgSiDM8mr143FloiQuoXHu6VnjRZH1dlaHhyyJLfz_sixpGvDeniVENEQ
```

### 未在header中添加token

```bash
dew@diyu204:~/workshop/github.com/auth$ curl http://127.0.0.1:8081/
认证失败
```

### 不需要验证token见WebSecurityConfig中代码

```bash
dew@diyu204:~/workshop/github.com/auth$ curl http://127.0.0.1:8081/getAll
[{"id":1,"gmtCreate":"2020-04-13T05:14:45.000+0000","gmtModified":"2020-04-13T05:14:45.000+0000"},{"id":2,"gmtCreate":"2020-04-13T05:14:51.000+0000","gmtModified":"2020-04-13T05:14:51.000+0000"},{"id":500,"gmtCreate":"2020-04-13T05:14:59.000+0000","gmtModified":"2020-04-13T05:14:59.000+0000"}]
```

### 权限不足 在WebSecurityConfig中代码中说明或是在controller中说明

```bash
dew@diyu204:~/workshop/github.com/auth$ curl -H "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJYWC10ZXN0LVhYIiwiYXVkIjoidXNlcjEiLCJpc3MiOiJ5dXhpIiwiZXhwIjoxNTg2NzY1NDMwMjMzLCJpYXQiOjE1ODY3NTgyMzAyMzMsImp0aSI6IjA4ZGNjZjkyLWY1MjEtNGZiNS1hODk2LTEzNzgxMDZmZDFjZiJ9.5t7yDLQJXhWbHUUaUPLb7N5jTG0c3V-IXgMRMryADqH3iO9cPNatPM5jFQfkmGqxChbr7rPvZTmwveSE0u8FeA" http://127.0.0.1:8081/level3/3
权限不足
```

### 带token访问

```bash
dew@diyu204:~/workshop/github.com/auth$ curl -H "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJYWC10ZXN0LVhYIiwiYXVkIjoidXNlcjEiLCJpc3MiOiJ5dXhpIiwiZXhwIjoxNTg2NzY1NDMwMjMzLCJpYXQiOjE1ODY3NzMsImp0aSI6IjA4ZGNjZjkyLWY1MjEtNGZiNS1hODk2LTEzNzgxMDZmZDFjZiJ9.5t7yDLQJXhWbHUUaUPLb7N5jTG0c3V-IXgMRMryADqH3iO9cPNatPM5jFQfkmGqxChbr7rPvZTmwveSE0u8FeA" http://127.0.0.1:8081/level1/1
level1/1
```

### 带错误的token

```bash
dew@diyu204:~/workshop/github.com/auth$ m/auth$ curl -H "Authorization:Bearer 123456" http://127.0.0.1:8081/level2/2
认证失败
```

也可使用Postman查看