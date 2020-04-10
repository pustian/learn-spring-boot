//package utils;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import tian.pusen.utils.JwtUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class JwtUtilTest {
//    public static void main(String[] args) throws InterruptedException {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        UserDetails userDetails = new User("tianpusen", "1234", authorities);
//        String token = JwtUtil.sign(userDetails);
//        System.out.println(token);
//        JwtUtil.validateToken(token, userDetails);
//
//        userDetails = new User("tianpusen", "1", authorities);
//        System.out.println(JwtUtil.validateToken(token, userDetails) );
//
//        userDetails = new User("tianpusen1", "1", authorities);
//        System.out.println(JwtUtil.validateToken(token, userDetails) );
//
//        Thread.sleep(5*1000L + 10);
//        userDetails = new User("tianpusen", "12", authorities);
//
//        System.out.println(JwtUtil.validateToken(token, userDetails) );
//    }
//}
