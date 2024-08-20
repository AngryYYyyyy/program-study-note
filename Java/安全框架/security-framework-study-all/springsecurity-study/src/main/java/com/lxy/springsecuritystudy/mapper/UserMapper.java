package com.lxy.springsecuritystudy.mapper;

import com.lxy.springsecuritystudy.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author AngryYY
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2024-08-19 19:54:19
* @Entity com.lxy.springsecuritystudy.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过用户ID查询对应的角色键值。
     * 此方法通过联表查询在sys_user, sys_user_role, 以及sys_role中获取指定用户的角色键值。
     *
     * @param userId 用户的唯一标识ID
     * @return 返回对应用户的角色键值，如果没有找到对应数据则返回null
     */
    @Select("SELECT r.role_key FROM sys_user u INNER JOIN sys_user_role ur ON u.user_id = ur.user_id INNER JOIN sys_role r ON ur.role_id = r.role_id WHERE u.user_id = #{userId}")
    List<String> getRolesForUser(Long userId);

    @Select("SELECT *FROM sys_user WHERE  user_name=#{username}")
    User getUserByUserName(String username);
}




