package com.lxy.characterization;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/11 21:58
 * @Description：
 */
public class TestSyn {
    public static Integer count=0;
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                synchronized (TestSyn.class) {
                    count++;
                }
            }
        });
        t1.start();
        for (int i = 0; i < 10000; i++) {
            synchronized (TestSyn.class) {
                count++;
            }
        }
        /*等待t1线程完成*/
        t1.join();
        System.out.println(count);
    }
}
