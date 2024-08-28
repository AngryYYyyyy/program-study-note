package com.lxy;


import com.lxy.example.mapper.DeptMapper;
import com.lxy.example.mapper.EmpMapper;
import com.lxy.example.model.entity.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/17 14:54
 * @Description：
 */
public class MybatisTest {
    @Test
    public void test() throws IOException {
        /*解析mybatis-config.xml配置文件*/
        InputStream resource = Resources.getResourceAsStream("mybatis-config.xml");
        /*构建SqlSession工厂*/
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resource);
        /*构建sql会话*/
        SqlSession session = factory.openSession();
        /*执行sql*/
        List<Emp> listEmp = session.selectList("com.lxy.example.mapper.EmpMapper.listEmp");
        DeptMapper mapper = session.getMapper(DeptMapper.class);

        listEmp.forEach(System.out::println);
        /*关闭sql会话*/
        session.close();
    }
    @Test
    public void test1() throws IOException {
        /*解析mybatis-config.xml配置文件*/
        InputStream resource = Resources.getResourceAsStream("mybatis-config.xml");
        /*构建SqlSession工厂*/
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(resource);
        /*构建sql会话*/
        SqlSession session = factory.openSession();
        /*获取代理对象*/
        EmpMapper mapper = session.getMapper(EmpMapper.class);
        /*调用接口方法*/
        List<Emp> listEmp = mapper.listEmp();
        listEmp.forEach(System.out::println);
        /*关闭sql会话*/
        session.close();
    }
}