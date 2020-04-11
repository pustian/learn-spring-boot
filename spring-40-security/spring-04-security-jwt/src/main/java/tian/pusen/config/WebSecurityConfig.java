package tian.pusen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)//保证post之前的注解可以使用
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JWTAuthorizationTokenFilter authenticationTokenFilter;
    //主要进行验证的地方
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
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
            .antMatchers("/login").permitAll()
            .antMatchers("/haha").permitAll()
            .antMatchers("/auth/test").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").anonymous()
            .anyRequest().authenticated();       // 剩下所有的验证都需要验证
        // 这里面主要配置如果没有凭证，可以进行一些操作，
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        // 是为了方便后面写前后端分离的时候前端过来的第一次验证请求，这样做，会减少这种请求的时间和资源使用。
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
