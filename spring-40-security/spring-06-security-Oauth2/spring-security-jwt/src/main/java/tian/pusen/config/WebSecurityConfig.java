package tian.pusen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tian.pusen.config.error.AccessAuthenticationDenied;
import tian.pusen.config.error.JWTAuthenticationEntryPoint;

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
//            /*level1路径下需要VIP2身份才能访问*/
//            .antMatchers("/level2/**").hasRole("VIP2")
//            /*level1路径下需要VIP3身份才能访问*/
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
