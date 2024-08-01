package com.lxy.chain;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 20:32
 * @Description：
 */
abstract class Logger {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    protected int level;
    //责任链中的下一个元素
    protected Logger nextLogger;

    public Logger setNextLogger(Logger nextLogger){
        this.nextLogger = nextLogger;
        return this;
    }

    public void logMessage(int level, String message){
        if(this.level <= level){
            write(message);
        }
        if(nextLogger != null){
            nextLogger.logMessage(level, message);
        }
    }

    abstract protected void write(String message);
}
