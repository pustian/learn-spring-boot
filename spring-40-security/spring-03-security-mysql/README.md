结合mybatis 和security

权限问题
```java
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        /*从内存中读取认证*/
//        auth.inMemoryAuthentication()
//                /*Spring Security 5.0开始必须要设置加密方式 */
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user1").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP1", "VIP2")
//                .and()
//                .withUser("user2").password(new BCryptPasswordEncoder().encode("123456")).roles("VIP2", "VIP3")
//                .and()
//                .withUser("user3").password(new BCryptPasswordEncoder().encode("123456")).roles(new String[]{"VIP3", "VIP1"});
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(operatorService);
    }
```
Q：
```log
java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
	at org.springframework.security.crypto.password.DelegatingPasswordEncoder$UnmappedIdPasswordEncoder.matches(DelegatingPasswordEncoder.java:250) ~[spring-security-core-5.2.2.RELEASE.jar:5.2.2.RELEASE]
```
A:
```
1, new BCryptPasswordEncoder().encode("123456") 加密后的值存储到数据库
    update operator  set password  = '$2a$10$3TTY12O7vb6AG.R5vCk2pu2ApLJuAfqXJqNE/NWPPkOPuSBkEx0TS'
2, 
             auth.userDetailsService(operatorService).passwordEncoder(new BCryptPasswordEncoder());
```
类似的可以使用其他加密
PasswordEncoder实现类包含
+ BCryptPasswordEncoder (org.springframework.security.crypto.bcrypt)
+ DelegatingPasswordEncoder (org.springframework.security.crypto.password)
+ LdapShaPasswordEncoder (org.springframework.security.crypto.password)
+ MessageDigestPasswordEncoder (org.springframework.security.crypto.password)
+ StandardPasswordEncoder (org.springframework.security.crypto.password)
+ Md4PasswordEncoder (org.springframework.security.crypto.password)
+ LazyPasswordEncoder in WebSecurityConfigurerAdapter (org.springframework.security.config.annotation.web.configuration)
+ LazyPasswordEncoder in AuthenticationConfiguration (org.springframework.security.config.annotation.authentication.configuration)
+ AbstractPasswordEncoder (org.springframework.security.crypto.password)
+ Pbkdf2PasswordEncoder (org.springframework.security.crypto.password)
+ Argon2PasswordEncoder (org.springframework.security.crypto.argon2)
+ NoOpPasswordEncoder (org.springframework.security.crypto.password)
+ UnmappedIdPasswordEncoder in DelegatingPasswordEncoder (org.springframework.security.crypto.password)
+ SCryptPasswordEncoder (org.springframework.security.crypto.scrypt)

StandardPasswordEncoder 这个替换没有问题
