package com.msb.hjycommunity.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义填充控制器
 * @author spikeCong
 * @date 2023/3/10
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //insert时 要填充的字段
    @Override
    public void insertFill(MetaObject metaObject) {

        //根据属性名称设置要填充的字段
        this.strictInsertFill(metaObject,"createBy",String.class,"admin");
        this.strictInsertFill(metaObject,"updateBy",String.class,"admin");
        this.strictInsertFill(metaObject,"createTime", Date.class,new Date());
        this.strictInsertFill(metaObject,"updateTime", Date.class,new Date());
    }

    //在update操作的时候 要填充的字段
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updateBy",String.class,"admin");
        this.strictUpdateFill(metaObject,"updateTime", Date.class,new Date());
    }

}
