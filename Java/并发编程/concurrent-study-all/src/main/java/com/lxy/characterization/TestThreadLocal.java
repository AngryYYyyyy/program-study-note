package com.lxy.characterization;

import com.lxy.Main;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/11 21:58
 * @Description：
 */
public class TestThreadLocal {
    /*创建线程独立的副本*/
    private static ThreadLocal<Integer> threadLocalCount=ThreadLocal.withInitial(() -> 0);
    static class Run implements Runnable {
        @Override
        public void run() {
            Integer count = threadLocalCount.get();
            for (int i = 0; i < 10000; i++) {
                threadLocalCount.set(++count);
                System.out.println(Thread.currentThread().getName()+":"+threadLocalCount.get());
            }
            /*避免内存泄露*/
            threadLocalCount.remove();
        }
    }

    public static void main(String[] args) {
        new Thread(new Run()).start();
        new Thread(new Run()).start();
    }
}
