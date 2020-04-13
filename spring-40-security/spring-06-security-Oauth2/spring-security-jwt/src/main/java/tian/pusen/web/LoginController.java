package tian.pusen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.entity.Operator;
import tian.pusen.utils.JJwtTokenUtils;

@RestController
public class LoginController {
    @Autowired
//    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;


    @PostMapping("/auth/login")
    public String login(@RequestBody Operator sysUser){
        final UserDetails userDetails = userDetailsService.loadUserByUsername(sysUser.getUsername());
        final String token = JJwtTokenUtils.sign(userDetails);
        //添加 start
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //添加 end
        return token;
    }

//    @PostMapping("haha")
//    public String haha(){
//        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return "haha:"+userDetails.getUsername()+","+userDetails.getPassword();
//    }
}
