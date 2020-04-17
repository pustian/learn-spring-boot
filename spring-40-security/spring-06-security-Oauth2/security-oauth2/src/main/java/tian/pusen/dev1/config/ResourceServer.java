package tian.pusen.dev1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@Profile("dev1")
@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {
    // 同AuthorizationServer
    private static final String RESOURCE_IDS = "resource-ids";

    /**
     * 这里设置访问路径权限，相当于客户端url权限拦截，是可以单独独立出来
     */
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.requestMatchers().antMatchers("/users/**")
                .and().authorizeRequests().antMatchers("/users/**").authenticated();
        /*admins路径下需要ADMIN身份才能访问*/
        httpSecurity.authorizeRequests().antMatchers("/admins/**")
                .hasAnyRole("ADMIN").anyRequest().authenticated();
        ;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_IDS) // 配置资源id，这里的资源id和授权服务器中的资源id一致
                .stateless(true); // 设置这些资源仅基于令牌认证
    }

}
