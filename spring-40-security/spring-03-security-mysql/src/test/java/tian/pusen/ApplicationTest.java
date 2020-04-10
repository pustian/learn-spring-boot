package tian.pusen;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class ApplicationTest {
    public static void main(String[] args) {
        System.out.println(new StandardPasswordEncoder().encode("123456"));
    }
}
