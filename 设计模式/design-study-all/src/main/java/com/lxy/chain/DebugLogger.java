package com.lxy.chain;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 20:34
 * @Description：
 */
class DebugLogger extends Logger {
    public DebugLogger(int level){
        this.level = level;
    }

    protected void write(String message) {
        System.out.println("Debug Logger: " + message);
    }
}

