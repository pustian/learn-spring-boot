package tian.pusen.dev1.web;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Profile("dev1")
@RestController
public class TestController {
    @GetMapping(value = "/test")
    public Object test(){
        return "test";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")  //注解验证时默认加上前缀ROLE_，原因后面文章再讲
    @GetMapping(value = "/users")
    public List<String> userList(){
        return Arrays.asList("张三","李四","王五");
    }

    @GetMapping(value = "/users/{id}")
    public String userById(@PathVariable(name="id") String id){
        return "users/"+id;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/admins")
    public List<String> adminList(){
        return Arrays.asList("天皇","地皇", "人皇");
    }

    @GetMapping(value = "/admins/{id}")
    public String adminbyId(@PathVariable(name="id") String id){
        return "admin/" + id;
    }
}
