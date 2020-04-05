package tian.pusen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CurdController {
    @GetMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @GetMapping({"/", "index"})
    public String index() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

}
