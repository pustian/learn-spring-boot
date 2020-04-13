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
}
