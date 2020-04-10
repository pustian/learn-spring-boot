package tian.pusen.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tian.pusen.entity.Test;

import java.util.List;


@Mapper
public interface TestMapper {
    @Insert("insert into test values(#{id}, #{gmtCreate}, #{gmtModified})")
//    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    Integer insertRecord(Test test);
    @Select("select id, gmt_create, gmt_modified from test where id = ${id}")
    Test getById(Long id);
    @Select("select id, gmt_create, gmt_modified from test ")
    List<Test> getAll();
}
