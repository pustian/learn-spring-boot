package tian.pusen.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tian.pusen.entity.Operator;

import java.util.List;

@Mapper
public interface OperatorMapper {
    @Insert("INSERT INTO operator values(#{id}, #{username}, #{password}, #{roles} )")
    Integer insert(Operator operator);

    @Select("SELECT id, username, password, roles FROM operator " +
            "WHERE id = #{id}")
    Operator getById(Long id);

    @Select("SELECT id, username, password, roles FROM operator " +
            "WHERE username = #{username}")
    Operator getByUsername(String username);

    @Select("SELECT id, username, password, roles FROM operator " +
            "ORDER BY id ASC ")
    List<Operator> getAll();
}
