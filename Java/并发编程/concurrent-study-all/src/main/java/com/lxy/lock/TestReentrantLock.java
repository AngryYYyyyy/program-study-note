package com.lxy.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/12 14:34
 * @Description：
 */
public class TestReentrantLock {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(()->{
            lock.lock();
            try{
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName()+":" +i);
                    if(i==50){
                        condition.await();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }).start();
        Thread.sleep(10);
        lock.lock();
        try{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName()+":" +i);
            }
            condition.signal();
        }finally {
            lock.unlock();
        }
    }
}
