package tian.pusen;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class ApplicationTest {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        System.out.println(new BCryptPasswordEncoder().matches("123456", "$2a$10$rGy7eAglhsvkbzO4fALrQuWLdcsRosJhBk6gs9SCQhEOXP9zXkgeC"));

    }
//    $2a$10$rGy7eAglhsvkbzO4fALrQuWLdcsRosJhBk6gs9SCQhEOXP9zXkgeC
//    $2a$10$9lwTx5/HutNaUN5ZpXiPL.wD4fR7TDoUbdn/QaQFMh8mODD4nWyoO

}
