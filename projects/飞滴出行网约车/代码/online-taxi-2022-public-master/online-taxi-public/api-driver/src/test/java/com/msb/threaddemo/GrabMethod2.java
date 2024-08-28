package com.msb.threaddemo;

/**
 * 将抢单核心逻辑做个包装
 */
public class GrabMethod2 extends Thread{

    public void run(){
        GrabCoreLogic2 s = new GrabCoreLogic2();
        s.sync();
    }
}