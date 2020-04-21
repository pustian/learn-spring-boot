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

+ 生成 JKS Java KeyStore 文件oauth.jks

```bash
keytool -genkeypair -alias mytest -keyalg RSA -keypass pustian-pass -keystore oauth.jks -storepass pustian
您的名字与姓氏是什么?
  [Unknown]:  pustian
您的组织单位名称是什么?
  [Unknown]:  dew
您的组织名称是什么?
  [Unknown]:  dew
您所在的城市或区域名称是什么?
  [Unknown]:  Sh
您所在的省/市/自治区名称是什么?
  [Unknown]:  Sh
该单位的双字母国家/地区代码是什么?
  [Unknown]:  CN
CN=pustian, OU=dew, O=dew, L=Sh, ST=Sh, C=CN是否正确?
  [否]:  Y
```

+ 导出公钥  密钥是storepass 

```bash
keytool -list -rfc --keystore  oauth.jks | openssl x509 -inform pem -pubkey
输入密钥库口令:  pustian

这段时公钥
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqLNIqAUanElZfv9e+Z5K
qe3isx08W4crplDGOUrb2Tr2WZZXHnSW5gqX6IZ7eD2wPDAFfHh0C128pq9Ct1el
MdWDeUVqSGIqOHRN1FRiomxSAHzWLQkM6Jfd+VCcB6o/PpPJgYgBYr92BtchnnfT
/fsqXFnpIPeAEx84cRwRfasq8ia1yl1DFrt/oSlJapS8bgNMCYBZ5V8o10Kl2B9Y
N8W9dSKXVunzDNOANsJCaxazKQrZjrVVamgxcmMCmCdNf2CtpLeY8HLXUqg481dN
nYeb8ToqmnSxGO4eXgPDEubfL0LZDVklSndhoedvq2Y6ToMEs7CwR6TfcfRANBYA
KwIDAQAB
-----END PUBLIC KEY-----
```
将文件oauth.jks 放到resources 目录下

## dev3-jwt存储令牌 令牌声明，添加额外的属性
CustomTokenEnhancer.java
AuthorizationServer


