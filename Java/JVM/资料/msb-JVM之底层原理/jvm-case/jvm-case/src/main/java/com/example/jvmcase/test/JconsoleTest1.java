package com.example.jvmcase.test;


import com.google.common.collect.Lists;

import java.util.ArrayList;

public class JconsoleTest1 {
    //将bytes设置为全局变量
    public byte[] bytes = new byte[1024*1024];
    //测试内存的变化情况
    public static void main(String[] args) {
        try {
            Thread.sleep(3000);//为了能看到效果
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("开始..");
        fillHeap(1000);
    }

    public static void fillHeap(int count){
        ArrayList<JconsoleTest1> jts = Lists.newArrayList();

        for(int i=0;i<count;i++){
            try {
                //这里睡3S，出效果
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jts.add(new JconsoleTest1());
        }
    }
}

