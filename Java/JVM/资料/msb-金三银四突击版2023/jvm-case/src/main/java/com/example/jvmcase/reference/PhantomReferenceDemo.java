package com.example.jvmcase.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WeakReferenceDemo
 * @Title：
 * @Author: Carl
 * @Date: 2020/12/3 0003 16:38
 * @Description:TODO
 */

public class PhantomReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        Object value = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
        Thread thread = new Thread(() -> {
            try {
                int cnt = 0;
                WeakReference<byte[]> k;
                while ((k = (WeakReference) referenceQueue.remove()) != null) {
                    System.out.println((cnt++) + "回收了:" + k);
                }
            } catch (InterruptedException e) {
                //结束循环
            }
        });
        thread.setDaemon(true);
        thread.start();


        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            byte[] bytes = new byte[1024 * 1024];
            WeakReference<byte[]> weakReference = new WeakReference<byte[]>(bytes, referenceQueue);
            map.put(weakReference, value);
        }
        System.out.println("map.size->" + map.size());


    }
}
