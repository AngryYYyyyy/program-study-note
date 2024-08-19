package com.lxy.springsecuritystudy.mapper;

import com.lxy.springsecuritystudy.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
* @author AngryYY
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2024-08-19 19:54:19
* @Entity com.lxy.springsecuritystudy.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




