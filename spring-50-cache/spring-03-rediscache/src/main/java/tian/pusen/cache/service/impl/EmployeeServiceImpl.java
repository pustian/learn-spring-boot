package tian.pusen.cache.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tian.pusen.cache.entity.Employee;
import tian.pusen.cache.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Override
    public Integer insert(Employee employee) {
        System.out.println("Insert employee " + employee.getId());
        return 1;
    }

    @Override
    public Integer update(Employee employee) {
        System.out.println("Update employee " + employee.getId());
        return 1;
    }

//    @Cacheable(value = "emp", key = "#id")
    @Override
    public Employee getById(Integer id) {
        System.out.println("get employee " + id);
        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("hello");
        return employee;
    }
}
