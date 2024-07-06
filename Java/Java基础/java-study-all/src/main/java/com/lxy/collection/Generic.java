package com.lxy.collection;

import java.util.ArrayList;

public class Generic {
    public static <T> void method(T l){
        System.out.println(l);
    }
    public static  void method(ArrayList<?> l){
        System.out.println(l);
    }
}
