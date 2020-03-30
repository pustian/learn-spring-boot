package tian.pusen.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@Controller
public class CurdController {
    @GetMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @GetMapping({"/", "index", "index.html"})
    public String index() {
        return "login";
    }

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username
            , @RequestParam("password") String password
            ,Model model
    ) {
        if("123456".equals(password) ) {
            return "dashboard";
        } else {
            model.addAttribute("msg","ERROR:");
            return  "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

}
