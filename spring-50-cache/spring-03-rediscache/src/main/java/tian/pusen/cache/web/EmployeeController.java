package tian.pusen.cache.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.cache.entity.Employee;
import tian.pusen.cache.service.IEmployeeService;

@RestController
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService;

    @GetMapping("/employee/{id}")
    @Cacheable(value = "emp", key = "#id")
    public Employee getById(@PathVariable Integer id) {
        System.out.println("get By ID");
        return employeeService.getById(id);
    }
}
