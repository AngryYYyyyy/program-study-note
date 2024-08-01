package com.lxy.example.mapper;

import com.lxy.example.model.entity.Emp;
import java.util.List;

/**
* @author AngryYY
* @description 针对表【emp】的数据库操作Mapper
* @createDate 2024-07-19 09:45:15
* @Entity com.lxy.entity.Emp
*/
public interface EmpMapper {
    List<Emp> listEmp();
}




