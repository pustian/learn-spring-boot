https://www.jianshu.com/p/227f7e7503cb
authrization-server(授权服务器)

## dev0-基于内存存储令牌
```pom.xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework.security.oauth/spring-security-oauth2 -->
        <dependency>
            <groupId>org.springframework.security.oauth</groupId>
            <artifactId>spring-security-oauth2</artifactId>
            <version>2.3.3.RELEASE</version>
        </dependency>
```

### 浏览器获取code

```
http://localhost:8001/oauth/authorize?client_id=client-id&response_type=code&scope=read_profile&redirect_uri=http://localhost:8001/api/hello
```
进入登陆页面输入用户名密码
UserServiceDetail 中的user/passwd

+ 此处密码的都一样
授权后，跳转到页面
```
http://localhost:8001/api/hello?code=zj9Nw1
```
### 获取Access_token

postman 
POST Request 地址 http://localhost:8001/oauth/token
Params: 

```
code=uGxPdz
grant_type=authorization_code
redirect_uri=http://localhost:8001/api/hello
```
访问需要设置 Authorization 
选取 Basic Auth
UserName: 填写client_id 值 
Password: 填写client_secret 值
```
获得如下的返回值
​```bash
{
    "access_token": "d685e36a-4776-48ea-8045-8c1d4beaa0ea",
    "token_type": "bearer",
    "expires_in": 43108,
    "scope": "read_profile"
}
```

以下未测试过
## dev1-jdbc存储令牌

### schema.sql

https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql

### pom.xml

```pom.xml
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
```

application.yaml

```yaml
spring:
        datasource:
            driver-class-name: com.mysql.jdbc.Driver
            url: jdbc:mysql://localhost:3306/client?useUnicode=yes&characterEncoding=UTF-8
            username: root
            password: 123456ly
```

oauth_client_details 表添加客户端信息

```sql
client_id:
client_secret:
scope:
authorization_grant_types: authorization_code
redirect_uri
```

## dev2-jwt 存储令牌

+ 生成 JKS Java KeyStore 文件

```bash
keytool -genkeypair -alias mytest -keyalg RSA -keypass mypass -keystore mytest.jks -storepass mypass
```

+ 导出公钥

```bash
keytool -list -rfc --keystore mytest.jks | openssl x509 -inform pem -pubkey
```
将文件放到resources 目录下

## dev3-jwt存储令牌 令牌声明，添加额外的属性
CustomTokenEnhancer.java
AuthorizationServer


