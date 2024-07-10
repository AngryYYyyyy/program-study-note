package com.example.jvmcase.optimize;

import com.example.jvmcase.domain.Worker;

public class Demo2 {

    //方法中定义的对象被外部所引用    对象逃逸了
    public Worker getWorkObject() {
        Worker work = new Worker ();
        work .setId(1);
        work .setUsername("yzt");
        work .setPassword("123456");
        // ......
        return work ;
    }

    //方法中的对象没有被外部所引用    对象没逃逸
    public void setWorkObject() {
        Worker work = new Worker ();
        work .setId(1);
        work .setUsername("yzt");
        work .setPassword("123456");

    }
}
