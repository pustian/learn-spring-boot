package tian.pusen.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("hello")
    public String hello(@RequestParam(defaultValue = "world") String name) {
        return "hello " + name;
    }
    @GetMapping("v1/helloV1")
    public String hellov1(@RequestParam(defaultValue = "world") String name) {
        return "v1 hello " + name;
    }
}
