package tian.pusen.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tian.pusen.entity.Operator;
import tian.pusen.mapper.OperatorMapper;
import tian.pusen.service.IOperatorService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OperatorServiceImpl implements IOperatorService {
    @Autowired
    OperatorMapper operatorMapper;

    @Override
    public Integer insert(Operator user) {
        return operatorMapper.insert(user);
    }

    @Override
    public Operator getById(Long id) {
        return operatorMapper.getById(id);
    }

    @Override
    public Operator getByUsername(String username) {
        return operatorMapper.getByUsername(username);
    }

    @Override
    public List<Operator> getAll() {
        return operatorMapper.getAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails result = null;
        Operator operator = operatorMapper.getByUsername(username);
        try {
            if (operator == null) {
                throw new UsernameNotFoundException("用户名不存在");
            }
            //用户权限
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (StringUtils.isNotBlank(operator.getRoles())) {
                String[] roles = operator.getRoles().split(",");
                for (String role : roles) {
                    if (StringUtils.isNotBlank(role)) {
                        authorities.add(new SimpleGrantedAuthority(role.trim()));
                    }
                }
            }
            result = new User(operator.getUsername(), operator.getPassword(), authorities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
