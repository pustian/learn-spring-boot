package tian.pusen.data.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tian.pusen.data.entity.Element;
import tian.pusen.data.mapper.ElementMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElementMapperTest {
    @Autowired
    ElementMapper elementMapper;

    @Test
    public void getById() {
        Element element = elementMapper.selectById(1);
        System.out.println(element);
    }
}
