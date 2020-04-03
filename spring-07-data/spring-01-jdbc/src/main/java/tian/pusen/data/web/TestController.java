package tian.pusen.data.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/tests")
    public List<Map<String, Object>> test() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from test");
        return list;
    }
}
