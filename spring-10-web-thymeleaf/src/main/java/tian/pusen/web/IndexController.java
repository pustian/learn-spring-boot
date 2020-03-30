package tian.pusen.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tian.pusen.web.entity.Pet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class IndexController {
    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "index";
    }
//
//    @RequestMapping("/thymeleaf/request")
//    public String thymeleaf(HttpServletRequest request) {
//        request.setAttribute("message", "hello world request");
//        request.setAttribute("date", new Date());
//        return "thymeleaf/message";
//    }
//    // 无发正常映射
//    @RequestMapping("/thymeleaf/session")
//    public String thymeleaf(HttpServletRequest request, HttpSession session) {
//        session.setAttribute("message", "hello world request");
//        session.setAttribute("date", new Date());
//        return "thymeleaf/message";
//    }
//    // 无发正常映射
//    @RequestMapping("/thymeleaf/servletcontext ")
//    public String thymeleaf2(HttpServletRequest request, HttpSession servletcontext) {
//        ServletContext application = request.getServletContext();
//        application.setAttribute("message", "hello world request");
//        application.setAttribute("date", new Date());;
//        return "thymeleaf/message";
//    }
    @RequestMapping("/thymeleaf/model")
    public String thymeleafmodel(Model model) {
        model.addAttribute("message", "hello world model");
        model.addAttribute("date", new Date());
        return "thymeleaf/message";
    }
    @RequestMapping("/thymeleaf/modelandview")
    public ModelAndView thymeleaf() {
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("message", "hello world modelandview");
        modelAndView.addObject("date", new Date());
        modelAndView.setViewName("thymeleaf/message");
        return modelAndView;
    }

    @GetMapping("/thymeleaf/grammar")
    public String grammar(Model model) {
        model.addAttribute("welcome", "<h1>welcome to grammar test. </h1>" + "at " +new Date());

        Pet dog = new Pet();  dog.setName("旺财"); dog.setPrice(new BigDecimal("123.45")); dog.setSpecial("dog"); dog.setBirthday(new Date());
        Pet cat = new Pet();  cat.setName("进宝"); cat.setPrice(new BigDecimal("223.45")); cat.setSpecial("cat"); cat.setBirthday(new Date(0L));
        Pet snake = new Pet();snake.setName("妖孽"); snake.setPrice(new BigDecimal("999.99")); snake.setSpecial("snake");
//        Pet bird = null;
        Pet[] pets = new Pet[]{dog, cat, snake};
        model.addAttribute("pets", pets);

        Map<String, String> maps= new HashMap<>();
        maps.put("computer","Dell");
        maps.put("os","linux");
        maps.put("filesystem","ceph");
        model.addAttribute("maps", maps);

        return "thymeleaf/grammar";
    }
}
