package tian.pusen.mapper;

import org.springframework.stereotype.Repository;
import tian.pusen.entity.Test;

import java.util.List;

@Repository
public interface TestMapper {
    Integer insertRecord(Test test);
    Test getById(Long id);
    List<Test> getAll();
//    List<Test> getInterval();
}
