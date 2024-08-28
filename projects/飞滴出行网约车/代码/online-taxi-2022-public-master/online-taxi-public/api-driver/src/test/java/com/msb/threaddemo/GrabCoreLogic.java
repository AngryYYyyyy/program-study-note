package com.msb.threaddemo;

import java.util.Random;

/**
 * 模拟抢单的核心逻辑
 */
public class GrabCoreLogic {

    /**
     * 非静态方法
     */
    public static synchronized void sync(){
        int i = new Random().nextInt(20);
        System.out.println("线程开始:"+i);
        System.out.println("执行业务逻辑"+i);
        System.out.println("线程结束:"+i);

    }
}