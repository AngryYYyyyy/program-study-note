package com.lxy.collection;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Integer num=3;
        Generic.method(num);
        Generic.method(new ArrayList<Object>(){{add("a");add("b");add("c"); }});
    }
}
