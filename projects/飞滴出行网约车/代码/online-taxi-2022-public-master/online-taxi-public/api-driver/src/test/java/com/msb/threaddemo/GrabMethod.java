package com.msb.threaddemo;

/**
 * 将抢单核心逻辑做个包装
 */
public class GrabMethod extends Thread{

    public void run(){
        GrabCoreLogic s = new GrabCoreLogic();
        s.sync();
    }
}