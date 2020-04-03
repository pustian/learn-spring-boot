package tian.pusen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.entity.Test;
import tian.pusen.service.ITestService;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    ITestService testService;

    @GetMapping("/getAll")
    public List<Test> getAll() {
        return testService.getAll();
    }
}
