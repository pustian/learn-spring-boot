package tian.pusen.dev1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Profile("dev1")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    //配置用户存储在内存中，可以设置用户账号、密码、角色等
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password("{noop}123456")
                .roles("USER").build());
        manager.createUser(User.withUsername("admin").password("{noop}123456")
                .roles("ADMIN").build());
        return manager;
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    private AuthenticationManager authenticationManager;

    // 该对象用来将令牌信息存储到内存中
    @Autowired(required = false)
    TokenStore inMemoryTokenStore;

    // 该对象将为刷新token提供支持
    @Autowired
    UserDetailsService userDetailsService;

    private static final String CLIENT_ID = "client-id";
    private static final String SECRET = "{noop}authorize-secret";
    private static final String[] AUTHORIZED_GRANT_TYPES = {"authorization_code", "client_credentials", "password", "refresh_token"};
    private static final String SCOPES = "all";
    private static final String RESOURCE_IDS = "resource-ids";
    private static final String REDIRECT_URL = "";
    private static final Integer ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
    private static final Integer REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60;

    /**
     * 用来配置客户端详情服务（ClientDetailsService），
     * 客户端详情信息在这里进行初始化，能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     * clientId：（必须的）用来标识客户的Id。
     * secret：  （需要值得信任的客户端）客户端安全码，如果有的话。
     * authorizedGrantTypes：此客户端可以使用的权限（基于Spring Security authorities）
     * authorization_code：授权码类型、
     * implicit：隐式授权类型、
     * password：资源所有者（即用户）密码类型、
     * client_credentials：客户端凭据（客户端ID以及Key）类型、
     * refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。
     * scope：    用来限制客户端的访问范围，如果为空（默认）的话，那么客户端拥有全部的访问范围。
     * accessTokenValiditySeconds  accsess-token有效时长
     * refreshTokenValiditySeconds refresh-token有效时长
     * redirectUris    返回地址,可以理解成登录后的返回地址，可以多个。
     * 应用场景有:客户端swagger调用服务端的登录页面,登录成功，返回客户端swagger页面
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()         // .passwordEncoder(new BCryptPasswordEncoder()) // addPas
                .withClient(CLIENT_ID)
                .secret(SECRET)
                .authorizedGrantTypes(AUTHORIZED_GRANT_TYPES)
                .scopes(SCOPES)
                .resourceIds(RESOURCE_IDS) // 配置资源id
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
//                .redirectUris(REDIRECT_URL)
        ;
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * 访问地址：/oauth/token
     * 属性列表:
     * authenticationManager：认证管理器，当你选择了资源所有者密码（password）授权类型的时候，
     * 需要设置为这个属性注入一个 AuthenticationManager 对象。
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(inMemoryTokenStore); //配置令牌的存储（这里存放在内存中）
        //设置认证管理器
        endpoints.authenticationManager(authenticationManager);
        //设置访问/oauth/token接口，获取token的方式
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        endpoints.userDetailsService(userDetailsService);
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 表示支持 client_id 和 client_secret 做登录认证
        security.allowFormAuthenticationForClients()
//                .tokenKeyAccess("permitAll()")         //url:/oauth/token_key,  exposes public key for token verification if using JWT tokens
//                .checkTokenAccess("isAuthenticated()") //url:/oauth/check_token allow check token
        ;
    }

}
