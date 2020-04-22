package tian.pusen.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "world") String name) {
        return "hello " + name;
    }
}
