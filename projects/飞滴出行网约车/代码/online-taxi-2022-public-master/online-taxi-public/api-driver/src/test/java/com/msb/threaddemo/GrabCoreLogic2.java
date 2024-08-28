package com.msb.threaddemo;

import java.util.Random;

/**
 * 模拟抢单的核心逻辑
 */
class GrabCoreLogic2 {

    public void sync(){
        synchronized (GrabMethod2.class){
            int i = new Random().nextInt(100);
            System.out.println("线程开始:"+i);
            System.out.println("执行业务逻辑"+i);
            System.out.println("线程结束:"+i);
        }


    }
}