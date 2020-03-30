package tian.pusen.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
    @RequestMapping(value = {"/home", "/index"})
    public String home(Model model) {
        return "index.html";
    }
}
