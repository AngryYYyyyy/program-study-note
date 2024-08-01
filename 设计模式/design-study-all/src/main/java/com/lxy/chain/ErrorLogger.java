package com.lxy.chain;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 20:35
 * @Description：
 */
class ErrorLogger extends Logger {
    public ErrorLogger(int level){
        this.level = level;
    }

    protected void write(String message) {
        System.out.println("Error Logger: " + message);
    }
}

