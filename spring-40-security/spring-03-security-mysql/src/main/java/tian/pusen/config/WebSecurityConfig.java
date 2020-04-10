package tian.pusen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import tian.pusen.service.IOperatorService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IOperatorService operatorService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*匹配所有路径的*/
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
        http.formLogin()
                /*
                    定制自己的登录界面
                    默认username字段提交用户名，可以通过usernameParameter自定义
                    默认password字段提交密码，可以用过passwordParameter自定义
                    定制了登录页面后
                    登录页面地址的POST请求就是登录请求，可以用过loginProcessingUrl自定义
                  */
                .loginPage("/userlogin");
//                .and()
        /*开启注销功能,访问/logout并清空session。 默认注销成功后会返回 login?logout */
        http.logout()
                /*重定向注销成功后返回页面*/
                .logoutSuccessUrl("/");
//                .and()
//                .exceptionHandling()
//                /*设置403错误跳转页面*/
//                .accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
        /*开启记住我功能，登录会添加Cookie,点击注销会删除Cookie*/
        http.rememberMe()
                /*设置记住我参数*/
                .rememberMeParameter("remember");
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
