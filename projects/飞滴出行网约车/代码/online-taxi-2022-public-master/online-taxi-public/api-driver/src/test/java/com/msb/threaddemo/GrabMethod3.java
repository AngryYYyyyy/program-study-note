package com.msb.threaddemo;

/**
 * 将抢单核心逻辑做个包装
 */
public class GrabMethod3 extends Thread{

    public void run(){
        GrabCoreLogic3 s = new GrabCoreLogic3();
        s.sync();
    }
}