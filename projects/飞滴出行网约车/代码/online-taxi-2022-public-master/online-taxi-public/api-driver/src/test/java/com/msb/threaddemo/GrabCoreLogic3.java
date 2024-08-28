package com.msb.threaddemo;

import java.util.Random;

/**
 * 模拟抢单的核心逻辑
 */
class GrabCoreLogic3 {

    public void sync(){
        synchronized ("evtest"){
            int i = new Random().nextInt(10);
            System.out.println("线程开始:"+i);
            System.out.println("执行业务逻辑"+i);
            System.out.println("线程结束:"+i);
        }


    }
}