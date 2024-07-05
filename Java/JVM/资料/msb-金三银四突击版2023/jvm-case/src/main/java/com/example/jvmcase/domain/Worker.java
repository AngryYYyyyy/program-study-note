package com.example.jvmcase.domain;


import org.openjdk.jol.info.ClassLayout;


public class Worker {
    //    private Worker work;
    private int id;
    private String username;
    private String password;

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

//    public Worker getWork() {
//        return work;
//    }

//    public void setWork(Worker work) {
//        this.work = work;
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void printf(Worker p) {
        // 查看对象的整体结构信息
        // JOL工具类
        System.out.println(ClassLayout.parseInstance(p).toPrintable());
    }
}
