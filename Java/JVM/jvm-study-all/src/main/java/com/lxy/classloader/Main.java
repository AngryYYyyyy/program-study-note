package com.lxy.classloader;
public class Main {
    static Integer age=18;
    public static String info;
    static{
        info="zhangMan:"+age;
    }
    public static void main(String[] args) {
        System.out.println(info);
    }
}
