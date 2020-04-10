package tian.pusen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tian.pusen.service.IOperatorService;

//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IOperatorService operatorService;
    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
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
                .antMatchers("/auth/**").permitAll()
                //其他的资源，全部需要认证；
                .anyRequest().authenticated();
        // 禁用缓存
        http.headers().cacheControl();

        // 添加JWT filter
        http.addFilterBefore(new JwtAuthenticationTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }


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
                auth.userDetailsService(operatorService).passwordEncoder(new StandardPasswordEncoder());

    }
}
