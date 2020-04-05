package tian.pusen.web.v1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;

@Controller
//@RequestMapping("/v1")
public class CurdController {
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello world";
    }

    @PostMapping("/user/login")
    public String login(@RequestParam("username") String username
            , @RequestParam("password") String password
            , Model model
            , HttpSession httpSession
                        ) {
        if("123456".equals(password) ) {
            httpSession.setAttribute("username", username);
            // 重定向到主页。避免表单重复提交
            return "redirect:/main.html";
//            return "dashboard";
        } else {
            model.addAttribute("msg","ERROR:");
            return  "login";
            // 此处如果重定向会导致，错误不可见
//            return "redirect:/index.html";
        }
    }

//    @GetMapping({"dashboard", "dashboard.html"})
//    public String dashboard() {
//        return "dashboard";
//    }

}
