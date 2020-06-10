package tian.pusen.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String Hello() {
        return "hello world";
    }

    @GetMapping("/v1/tests")
    public String[] test() {
        return new String[]{"田圃森", "田雨*"};
    }
}