package com.lxy;


import com.lxy.entity.User;
import com.lxy.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 14:54
 * @Description：
 */
public class MybatisTest {
    @Test
    public void test() throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession session = factory.openSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        List<User> users = mapper.selectAll();
        users.forEach(System.out::println);
    }

}