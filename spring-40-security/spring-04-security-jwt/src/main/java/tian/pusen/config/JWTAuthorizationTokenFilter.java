package tian.pusen.config;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tian.pusen.utils.JJwtTokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证成功当然就是进行鉴权了
 * 登录成功之后走此类进行鉴权操作
 */
@Component
public class JWTAuthorizationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private  UserDetailsService userDetailsService;

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 从header中获取凭证authToken
        String tokenHeader = request.getHeader(TOKEN_HEADER);
        String username = null;
        String authToken = null;

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            authToken = tokenHeader.substring(7);
            // token 验证
            username = JJwtTokenUtils.getAudienceFromToken(authToken);
            // 如果请求头中有token，则进行解析，并且设置认证信息
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (JJwtTokenUtils.validateToken(authToken, userDetails)) {
                    // 将用户信息存入 authentication，方便后续校验
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(TOKEN_PREFIX, "");
        String username = JJwtTokenUtils.getAudienceFromToken(token);
//        String role = JJwtTokenUtils.getRolesFromToken(token);
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, null, null
//                    Collections.singleton(new SimpleGrantedAuthority(role))
            );
        }
        return null;
    }
}
