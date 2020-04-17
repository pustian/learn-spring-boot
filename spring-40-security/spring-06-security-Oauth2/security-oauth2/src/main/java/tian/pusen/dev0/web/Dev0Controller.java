package tian.pusen.dev0.web;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("dev0")
@RestController
public class Dev0Controller {
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/api/hello")
    public String apiHello() {
        return "Hello World API";
    }
//
//    @GetMapping(value = "/test")
//    public Object test(){
//        return "test";
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_USER')")  //注解验证时默认加上前缀ROLE_，原因后面文章再讲
//    @GetMapping(value = "/users")
//    public List<String> userList(){
//        return Arrays.asList("张三","李四","王五");
//    }
//
//    @GetMapping(value = "/userById/{id}")
//    public String userById(@PathVariable(name="id") String id){
//        return "users/"+id;
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_USER')")
//    @GetMapping(value = "/userByName/{name}")
//    public String userByName(@PathVariable(name="name") String name) {
//        return "users/"+name;
//    }
//
//
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping(value = "/admins")
//    public List<String> adminList(){
//        return Arrays.asList("天皇","地皇", "人皇");
//    }
//
//    @GetMapping(value = "/admin/{id}")
//    public String adminbyId(@PathVariable(name="id") String id){
//        return "admin/" + id;
//    }
}
