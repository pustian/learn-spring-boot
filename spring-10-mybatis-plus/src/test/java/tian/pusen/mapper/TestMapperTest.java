package tian.pusen.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapperTest {
    @Autowired
    tian.pusen.mapper.TestMapper testMapper;
    @Test
    public void testInsert() {
        tian.pusen.entity.Test test = new tian.pusen.entity.Test();
//        test.setId(9998L);
        test.setGmtCreate(new Date(0L));
        test.setGmtModified(new Date(0L));
        int ret = testMapper.insertRecord(test);
        System.out.println(ret);
        Assert.assertTrue(ret > 0 );
    }
    @Test
    public void testGetById() {
        long l = 9999L;
        tian.pusen.entity.Test test = testMapper.getById(l);
        System.out.println(test);
        Assert.assertTrue(l == test.getId());
    }

    @Test
    public void testGet() {
        List<tian.pusen.entity.Test> list =  testMapper.getAll();
        System.out.println(list.size());
        Assert.assertTrue(list.size() >0);
    }
}
