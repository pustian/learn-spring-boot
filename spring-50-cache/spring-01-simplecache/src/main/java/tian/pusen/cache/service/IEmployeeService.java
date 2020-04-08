package tian.pusen.cache.service;

import tian.pusen.cache.entity.Employee;

public interface IEmployeeService {
    Integer insert(Employee employee);
    Integer update(Employee employee);
    Employee getById(Integer id);

}
