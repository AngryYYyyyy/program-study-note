package com.example.jvmcase.loader;


public class Demo5 {

    public static void main(String[] args) {
        //	String a = SubInitClass.a;// 引用父类的静态字段，只会引起父类初始化，而不会引起子类的初始化
        //	String b = InitClass.b;// 使用类的final常量不会引起类的初始化
        SubInitClass[] sc = new SubInitClass[10];// 定义类数组不会引起类的初始化
        String a = new String();
        a.getClass().getClassLoader();
        System.out.println(a.getClass().getClassLoader());
        System.out.println(new Demo5().getClass().getClassLoader());

    }

}

class InitClass {
    static {
        System.out.println("初始化InitClass");
    }

    public static String a = null;
    public final static String b = "b";

    public static void method() {
    }
}

class SubInitClass extends InitClass {
    @Override
    public String toString() {
        return super.toString();
    }

    static {
        System.out.println("初始化SubInitClass");
    }
}

