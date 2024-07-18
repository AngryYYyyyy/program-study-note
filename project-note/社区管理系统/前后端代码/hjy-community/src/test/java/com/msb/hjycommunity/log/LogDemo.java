package com.msb.hjycommunity.log;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author spikeCong
 * @date 2023/4/2
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LogDemo {

    Logger logger = LoggerFactory.getLogger(LogDemo.class);

    @Test
    public void test1(){

        logger.info("Hello LogBack! ! !");
    }

    /**
     * 传统方式实现日志打印
     * @param
     */
    @Test
    public void test2(){
        logger.trace("这个级别很少用,主要是用来追踪! !");
        logger.debug("经常写BUG的程序员,测试的时候,多打印日志 没毛病!");
        logger.info("系统日志,没有什么问题,就是想打印日志 !");
        logger.warn("这个错误很少见,不影响程序继续运行,酌情处理!");
        logger.error("发生了严重的错误,程序阻断了,需要立即处理,发送警报!");
    }

    /**
     * @Slf4j注解方式实现日志打印
     * @param
     */
    @Test
    public void test3(){
        log.trace("这个级别很少用,主要是用来追踪! !");
        log.debug("经常写BUG的程序员,测试的时候,多打印日志 没毛病!");
        log.info("系统日志,没有什么问题,就是想打印日志 !");
        log.warn("这个错误很少见,不影响程序继续运行,酌情处理!");
        log.error("发生了严重的错误,程序阻断了,需要立即处理,发送警报!");
    }

    /**
     * 日志打印的规范
     *      1.要使用log输出Exception的全部信息
     *      2.增加对低级别日志的判断
     *      3.尽量使用占位符 而不是拼接方式打印日志
     */
    @Test
    public void test4(){

        //尽量使用占位符 而不是拼接方式打印日志
        String name = "我是大佬";
        log.info("hello " + name );
        log.debug("hello {}", name); //推荐使用

        String userId = "100010";
        String orderId = "4681764538179564";
        log.debug("记录当前订单的 userId:[{}] 和 orderId:[{}] ",userId,orderId);


        //要使用log输出Exception的全部信息
        try {
            int i = 1 / 0;
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("/ by zero", e);
        }

        //增加对低级别日志的判断
        //执行顺序: 先进行字符串的拼接,然后执行Debug ,判断日志级别 ,比如当前是info 那么就不打印了
        log.debug("hello " + name);

        //执行顺序: 不提前拼接,先判断日志级别.可以避免无用的字符串操作,提高性能
        if(log.isDebugEnabled()){
            log.debug("hello " + name);
        }

        /***
         * 日志打印的注意事项
         *      1.日志不能打断业务逻辑
         *      2.日志要能够反应功能是否正常
         *      3.不同类型的日志 分别输出
         *      4.日志保存周期不应过长
         */
    }

}
