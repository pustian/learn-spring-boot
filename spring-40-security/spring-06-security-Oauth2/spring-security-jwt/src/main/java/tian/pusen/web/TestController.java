package tian.pusen.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.entity.Test;
import tian.pusen.service.ITestService;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    ITestService testService;

    @GetMapping("/getAll")
    @ResponseBody
    public List<Test> getAll() {
        return testService.getAll();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PreAuthorize("hasAnyRole('VIP1')")
    @GetMapping("level1/1")
    public String one() {
        return "level1/1";
    }

    @PreAuthorize("hasAnyRole('VIP2')")
    @GetMapping("level2/2")
    public String two() {
        return "level2/2";
    }

    @PreAuthorize("hasAnyRole('VIP3')")
    @GetMapping("level3/3")
    public String three() {
        return "level3/3";
    }

}
