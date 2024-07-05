package com.example.jvmcase.constantPool;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Test {
    private static Unsafe unsafe = null;
    private static Field getUnsafe = null;

    static {
        try {
            getUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            getUnsafe.setAccessible(true);
            unsafe = (Unsafe) getUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    static boolean flag = true;
    public static void main(String[] args) throws Exception {
        new Thread(()->{
            while (flag){
//                unsafe.loadFence();
                unsafe.addressSize();
            }
            System.out.println("thread exist");
        }).start();
        Thread.sleep(1000);
        new Thread(()->{
            flag = false;
        }).start();

    }
}