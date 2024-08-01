package com.lxy.chain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/30 20:35
 * @Description：
 */
public class LoggerTest {
    @Test
    public void test() {
        Logger loggerChain=new InfoLogger(Logger.INFO).setNextLogger(
                new DebugLogger(Logger.DEBUG).setNextLogger(
                        new ErrorLogger(Logger.ERROR)
                )
        );

        loggerChain.logMessage(Logger.INFO, "This is an information.");
        loggerChain.logMessage(Logger.DEBUG, "This is a debug level information.");
        loggerChain.logMessage(Logger.ERROR, "This is an error information.");
    }
}