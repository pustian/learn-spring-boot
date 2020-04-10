package tian.pusen.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tian.pusen.entity.Operator;

import java.util.ArrayList;
import java.util.Collection;

public class JwtUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Collection<SimpleGrantedAuthority> authorities;

    public JwtUserDetails(Operator operator) {
        id = operator.getId();
        username = operator.getUsername();
        password = operator.getPassword();
        authorities = new ArrayList<SimpleGrantedAuthority>();
        if (StringUtils.isNotBlank(operator.getRoles())) {
            String[] roles = operator.getRoles().split(",");
            for (String role : roles) {
                if (StringUtils.isNotBlank(role)) {
                    authorities.add(new SimpleGrantedAuthority(role.trim()));
                }
            }
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
