package com.lxy.characterization;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/11 21:46
 * @Description：
 */
public class CAS {
    public static  AtomicInteger count = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                count.incrementAndGet();
            }
        });
        t1.start();
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet();
        }
        /*等待t1线程完成*/
        t1.join();
        System.out.println(count.get());
    }
}
