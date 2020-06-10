https://www.jianshu.com/p/227f7e7503cb
authrization-server(授权服务器)

## 授权服务配置
+ 授权类型（GrantType）、不同授权类型为客户端（Client）提供了不同的获取令牌（Token）方式，每一个客户端（Client）都能够通过明确的配置以及权限来实现不同的授权访问机制
+  @EnableAuthorizationServer 来配置授权服务机制，并继承 AuthorizationServerConfigurerAdapter 该类重写 configure 方法定义授权服务器策略

## 配置客户端详情（Client Details）
> clientId：客户端标识 ID
> secret：客户端安全码
> scope：客户端访问范围，默认为空则拥有全部范围
> authorizedGrantTypes：客户端使用的授权类型，默认为空
> authorities：客户端可使用的权限

## 管理令牌（Managing Token）
+ ResourceServerTokenServices 接口定义了令牌加载、读取方法
+ AuthorizationServerTokenServices 接口定义了令牌的创建、获取、刷新方法
+ ConsumerTokenServices 定义了令牌的撤销方法
+ DefaultTokenServices 实现了上述三个接口,它包含了一些令牌业务的实现，如创建令牌、读取令牌、刷新令牌、获取客户端ID。默认的当尝试创建一个令牌时，是使用 UUID 随机值进行填充的.
    + 除了持久化令牌是委托一个 TokenStore 接口实现以外，这个类几乎帮你做了所有事情
+ TokenStore 接口也有一些实现： 
    + InMemoryTokenStore：默认采用该实现，将令牌信息保存在内存中，易于调试
    + JdbcTokenStore：令牌会被保存近关系型数据库，可以在不同服务器之间共享令牌
    + JwtTokenStore：使用 JWT 方式保存令牌，它不需要进行存储，但是它撤销一个已经授权令牌会非常困难，所以通常用来处理一个生命周期较短的令牌以及撤销刷新令牌

#### JwtTokenStore JWT 令牌

+ 授权服务以及资源服务都需要配置这个转换类
  + 使用 JWT 令牌需要在授权服务中使用 JWTTokenStore，
  + 授权服务和资源服务器都需要一个解码 Token 令牌的类 JwtAccessTokenConverter，
  + JwtTokenStore 依赖这个类进行编码以及解码

+ Token 令牌默认是有签名的，并且资源服务器中需要验证这个签名，因此需要一个对称的 Key 值，用来参与签名计算
+ 这个 Key  值存在于授权服务和资源服务之中，或者使用非对称加密算法加密 Token 进行签名，Public Key 公布在 /oauth/token_key 这个 URL 中
+ 默认 /oauth/token_key 的访问安全规则是 "denyAll()" 即关闭的，可以注入一个标准的 SpingEL 表达式到 AuthorizationServerSecurityConfigurer 配置类中将它开启，例如 permitAll()
+ `需要引入 spring-security-jwt 库`

## 配置授权类型（Grant Types）

+ 授权是使用 AuthorizationEndpoint 这个端点来进行控制的，使用 AuthorizationServerEndpointsConfigurer 这个对象实例来进行配置，默认是支持除了密码授权外所有标准授权类型, 可配置以下属性
  + authenticationManager：认证管理器，当你选择了资源所有者密码（password）授权类型的时候，请设置这个属性注入一个 AuthenticationManager 对象
  + userDetailsService：可定义自己的 UserDetailsService 接口实现
    + 
  + authorizationCodeServices：用来设置收取码服务的（即 AuthorizationCodeServices 的实例对象），主要用于 "authorization_code" 授权码类型模式
  + implicitGrantService：这个属性用于设置隐式授权模式，用来管理隐式授权模式的状态
  + tokenGranter：完全自定义授权服务实现（TokenGranter 接口实现），只有当标准的四种授权模式已无法满足需求时

## 配置授权端点 URL（Endpoint URLs）

+ AuthorizationServerEndpointsConfigurer 配置对象有一个 pathMapping() 方法用来配置端点的 URL，它有两个参数：
  + 参数一：端点 URL 默认链接
  + 参数二：替代的 URL 链接

+  下面是一些默认的端点 URL：
  + /oauth/authorize：授权端点
  + /oauth/token：令牌端点
  + /oauth/confirm_access：用户确认授权提交端点
  + /oauth/error：授权服务错误信息端点
  + /oauth/check_token：用于资源服务访问的令牌解析端点
  + /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话

+ 授权端点的 URL 应该被 Spring Security 保护起来只供授权用户访问    


https://www.cnblogs.com/xugf/p/10720659.html
https://www.jianshu.com/p/68f22f9a00ee
https://www.jianshu.com/p/f6b73cd23114
