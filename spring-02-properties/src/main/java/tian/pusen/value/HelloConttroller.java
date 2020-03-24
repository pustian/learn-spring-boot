package tian.pusen.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloConttroller {
    @Value("${server.port}")
    private String name;
    @GetMapping("/hello")
    public String hello() {
        return "hello port:" + name;
    }
}
