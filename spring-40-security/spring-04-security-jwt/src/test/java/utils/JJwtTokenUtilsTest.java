//package utils;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import tian.pusen.utils.JJwtTokenUtils;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//public class JJwtTokenUtilsTest {
//
//    public static void main(String[] args) throws InterruptedException {
//        List< SimpleGrantedAuthority > authorities = new ArrayList<>();
//        UserDetails userDetails = new User("tianpusen", "1234", authorities);
//        String token = JJwtTokenUtils.generateToken(userDetails, null);
//
//        System.out.println(token);
//        System.out.println(JJwtTokenUtils.validateToken(token, userDetails) );
//
////        userDetails = new User("tianpusen", "1", authorities);
////        System.out.println(JJwtTokenUtils.validateToken(token, userDetails) );
//
////        userDetails = new User("tianpusen1", "1", authorities);
////        System.out.println(JJwtTokenUtils.validateToken(token, userDetails) );
//
//        Thread.sleep(5*1000L + 10);
//        userDetails = new User("tianpusen", "12", authorities);
//
//        System.out.println(JJwtTokenUtils.validateToken(token, userDetails) );
//
//    }
//}
