package com.example.jvmcase.loader;

public class LazyLoader {

    static {
        System.out.println("LazyLoader的静态代码块加载进来了");
    }

    public static void main(String[] args) {
        new Demo1();
        System.out.println("测试分行-------------------------");
        Demo2 demo2 = null;
    }

    static class Demo1 {
        static {
            System.out.println("Demo1的静态代码块加载进来了");
        }

        public Demo1() {
            System.out.println("初始化Demo1");
        }
    }

    static class Demo2 {
        static {
            System.out.println("Demo2的静态代码块加载进来了");
        }

        public Demo2() {
            System.out.println("初始化Demo2");
        }
    }
}
