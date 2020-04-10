package tian.pusen.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tian.pusen.entity.Operator;

import java.util.List;

public interface IOperatorService extends UserDetailsService {
    Integer insert(Operator user);

    Operator getById(Long id);

    Operator getByUsername(String username);

    List<Operator> getAll();
}
