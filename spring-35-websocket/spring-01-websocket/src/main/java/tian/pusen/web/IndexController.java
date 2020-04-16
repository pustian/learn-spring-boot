package tian.pusen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping(value = {"index"})
    public String index() {
        return "index";
    }

    @GetMapping(value = {"client"})
    public String client() {
        return "client";
    }

    @GetMapping(value = {"admin"})
    public String admin() {
        return "admin";
    }
}
