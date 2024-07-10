package com.example.jvmcase.loader;

import java.util.ArrayList;
import java.util.List;

public class LimitDemo {


    private List<Byte[]> list;

    public static void main(String[] args) {
        List<Byte[]> list = new ArrayList<>();
        int i = 0;
        try {
            while (true) {
                list.add(new Byte[1024 * 1024]);//每次1M
                i++;

            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("次数: " + i);
        }

    }
}
