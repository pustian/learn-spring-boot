//package tian.pusen.utils;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.AlgorithmMismatchException;
//import com.auth0.jwt.exceptions.InvalidClaimException;
//import com.auth0.jwt.exceptions.SignatureVerificationException;
//import com.auth0.jwt.exceptions.TokenExpiredException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.*;
//
//public final class JavaJwtTokenUtils {
////    iss：jwt签发者
////    sub：jwt所面向的用户
////    aud：接收jwt的一方
////    exp：jwt的过期时间，这个过期时间必须大于签发时间
////    nbf：定义在什么时间之前，该jwt都是不可用的
////    iat：jwt的签发时间
////    jti：jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击
//
//    private final static String TOKEN_SECRET = "pusen.tian";
//    private final static String SUBJECT = "XX-test-XX";
//    private final static String ISSUER = "yuxi";
//    private final static Long EXPIRATION = 7200L; // 2h = 2* 60 * 60 3L; //
//
//    public static String sign(String issuer, String subject, String username, Long expiration, Long issuedAt) {
//        // 私钥和加密算法
//        Algorithm algorithm = Algorithm.HMAC512(TOKEN_SECRET);
//
//        // 设置过期时间
//        Date date = new Date(System.currentTimeMillis() + EXPIRATION);
//
//        // 设置头部信息
//        Map<String, Object> header = new HashMap<>(2);
//        header.put("Type", "Jwt");
//        header.put("alg", "HMAC512");
//
//        // 返回token字符串
//        return JWT.create()
//                .withHeader(header)
//                .withJWTId(UUID.randomUUID().toString())
//                .withIssuer(issuer)
//                .withSubject(subject)
//                .withAudience(username)
//                .withExpiresAt(new Date(issuedAt + expiration * 1000L))
//                .withIssuedAt(new Date(issuedAt))
//                .sign(algorithm);
//    }
//
//    public static String sign(UserDetails userDetails) {
//        String username = userDetails.getUsername();
//        Long issuedAt = System.currentTimeMillis();
//        return sign(ISSUER, SUBJECT, username, EXPIRATION, issuedAt);
//    }
//
//    public static boolean validateToken(String token, String username) {
//        Algorithm algorithm = Algorithm.HMAC512(TOKEN_SECRET);
//        try {
//            JWTVerifier verifier = JWT.require(algorithm).build();
//            DecodedJWT jwt = verifier.verify(token);
//            List<String> aud = jwt.getAudience();
//            return aud.contains(username);
//        }catch (AlgorithmMismatchException | SignatureVerificationException
//                | TokenExpiredException | InvalidClaimException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//}
