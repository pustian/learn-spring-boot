package tian.pusen.redis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.redis.entity.User;
import tian.pusen.redis.util.IRedisService;

@RestController
public class UserController {
    @Autowired
    IRedisService redisService;

    @GetMapping("/{id}")
//    @Cacheable(value = {"user"},key = "#id")
    public User getById(@RequestParam(defaultValue = "1") String id) {
        User user = new User();
        user.setId(1);
        user.setLastName("pus");
        user.setEmail("123");
        user.setGender(1);
        user.setdId(1);
        redisService.set("user"+id, user);
        return user;
    }

}
