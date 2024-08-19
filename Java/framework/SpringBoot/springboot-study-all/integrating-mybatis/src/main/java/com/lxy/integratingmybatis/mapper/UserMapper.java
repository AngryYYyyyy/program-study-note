package com.lxy.integratingmybatis.mapper;

import com.lxy.integratingmybatis.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
* @author AngryYY
* @description 针对表【user】的数据库操作Mapper
* @createDate 2024-08-19 10:31:15
* @Entity com.lxy.integratingmybatis.model.entity.User
*/
@Mapper
public interface UserMapper  {
    @Insert("INSERT INTO user(id,name, age,email) VALUES(#{id},#{name},#{age}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectUserById(Integer id);

    @Update("UPDATE user SET name = #{name}, age=#{age},email = #{email} WHERE id = #{id}")
    void updateUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteUserById(Integer id);

    @Select("SELECT * FROM user")
    List<User> findAllUsers();
}




