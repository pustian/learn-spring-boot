package tian.pusen.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public final class JJwtTokenUtils  {
//    公共的声明： io.jsonwebtoken.Claims
//    公共的声明可以添加任何的信息，一般添加用户的相关信息或其它业务需要的必要信息，但不建议添加敏感信息，因为该部分在客户端可解密；
//    iss：jwt签发者
//    sub：jwt所面向的用户
//    aud：接收jwt的一方
//    exp：jwt的过期时间，这个过期时间必须大于签发时间
//    nbf：定义在什么时间之前，该jwt都是不可用的
//    iat：jwt的签发时间
//    jti：jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
//    私有的声明
//    私有的声明是提供者和消费者功能定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为名文信息。

    private final static String SECRET = "pusen.tian";
    private final static String SUBJECT = "XX-test-XX";
    private final static String ISSUER = "yuxi";
    private final static Long EXPIRATION = 7200L; // 2h = 2* 60 * 60

//    @Value("${jwt.token.issuer:Xhello}")
//    private String issuer;
//
//    @Value("${jwt.token.expiration:6000}")
//    private Long expiration; //过期时长，毫秒为单位
//
//    @Value("${jwt.token.notBefore:3600}")
//    private Long notBefore; // 毫秒为单位
//
//    @Value("${jwt.token.header}")
//    private String tokenHeader;

    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    // jwt的唯一身份标识
    public static String getIdFromToken(String token) {
        return getClaimFromToken(token, Claims::getId);
    }
    // jwt签发者
    public static String getIssuerFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuer);
    }
    // jwt所面向的用户
    public static String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    // 接收jwt的一方
    public static String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }
    // jwt的过期时间
    public static Date getExpirationFromToken(String token) {
        Date date = getClaimFromToken(token, Claims::getExpiration);
        return date;
    }
    // 定义在什么时间之前
    public static Date getNotBeforeFromToken(String token) {
        return getClaimFromToken(token, Claims::getNotBefore);
    }
//    // jwt的签发时间
//    public static Date getIssuedAtFromToken(String token) {
//        return getClaimFromToken(token, Claims::getIssuedAt);
//    }

    private static String sign(String issuer, String subject, String username, Long expiration, Long issuedAt,
                                       Map<String, Object> privateClaims) {
        Map<String, Object> header = new HashMap<>();
//        header.put("typ", "jwt");
//        header.put("alg", "HS256");

        Map<String, Object> claims = new HashMap<>();
        claims.put(Claims.ID, UUID.randomUUID());
        claims.put(Claims.ISSUER, issuer);
        claims.put(Claims.SUBJECT, subject);
        claims.put(Claims.AUDIENCE, username);
        claims.put(Claims.EXPIRATION,  new Date(issuedAt + expiration * 1000L) );
        claims.put(Claims.ISSUED_AT, new Date(issuedAt) );
//        System.out.println(claims.get(Claims.EXPIRATION));
        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .addClaims(privateClaims)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }


    public static String sign(UserDetails userDetails) {
         String username = userDetails.getUsername();
         Long issuedAt = System.currentTimeMillis();
        return sign(ISSUER, SUBJECT, username, EXPIRATION, issuedAt, null);
    }

    // 已超时返回 true
    private static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationFromToken(token);
        Long expir = expiration.getTime();
        Long current = System.currentTimeMillis();
        return current*1000 > expir;
    }

    public static Boolean validateToken(String token, String username) {
        final String tokenUsername = getAudienceFromToken(token);
        return null != tokenUsername && tokenUsername.equals(username) && isTokenExpired(token) == false
                && ISSUER.equals(getIssuerFromToken(token)) && SUBJECT.equals(getSubjectFromToken(token) )
                ;
    }

}
