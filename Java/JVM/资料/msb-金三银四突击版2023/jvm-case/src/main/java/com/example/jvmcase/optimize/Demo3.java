package com.example.jvmcase.optimize;

//栈上分配
public class Demo3 {

    public static void main(String[] args) {
        for (int i = 0; i < 5_000_000; i++) {
            createObject();
        }
    }

    public static void createObject() {
        new Object();
    }
}
