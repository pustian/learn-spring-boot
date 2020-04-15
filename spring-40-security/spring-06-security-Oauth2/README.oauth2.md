# OAuth 2.0

http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html

### 专用名词

1. Third-party application：第三方应用程序，本文中又称"客户端"（client）。
2. HTTP service：HTTP服务提供商，本文中简称"服务提供商"。

3. Resource Owner：资源所有者，本文中又称"用户"（user）。
4. User Agent：用户代理，本文中就是指浏览器。
5. Authorization server：认证服务器，即服务提供商专门用来处理认证的服务器。
6. Resource server：资源服务器，即服务提供商存放用户生成的资源的服务器。它与认证服务器，可以是同一台服务器，也可以是不同的服务器。

### OAuth 2.0的运行流程

![](./images/oauth2.0.png)

> （A）用户打开客户端以后，客户端要求用户给予授权。
>
> （B）用户同意给予客户端授权。
>
> （C）客户端使用上一步获得的授权，向认证服务器申请令牌。
>
> （D）认证服务器对客户端进行认证以后，确认无误，同意发放令牌。
>
> （E）客户端使用令牌，向资源服务器申请获取资源。
>
> （F）资源服务器确认令牌无误，同意向客户端开放资源。

### 客户端的授权模式

客户端必须得到用户的授权（authorization grant），才能获得令牌（access token）。OAuth 2.0定义了四种授权方式。

- 授权码模式（authorization code）

  ![](./images/authorization-code.png)

  > （A）用户访问客户端，后者将前者导向认证服务器。
  >
  > （B）用户选择是否给予客户端授权。
  >
  > （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。
  >
  > （D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。
  >
  > （E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。

- 简化模式（implicit）

- 密码模式（resource owner password credentials）

- 客户端模式（client credentials）

## Oauth2授权主要由两部分组成：

- Authorization server：认证服务

- Resource server：资源服务

  http://blog.itpub.net/69926045/viewspace-2646338/