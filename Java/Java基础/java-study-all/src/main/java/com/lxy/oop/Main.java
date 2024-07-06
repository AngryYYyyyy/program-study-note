package com.lxy.oop;

public class Main {
    public static void main(String[] args) {
        Person studentA=new Student(18,"zhangSan",2020);
        studentA.sleep();
        studentA.eat();
        ((Student)studentA).study();
        System.out.println(studentA);
    }
}
