package com.lxy.springsecuritystudy.utils;

import java.util.UUID;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/1 16:56
 * @Description：UUID生成器工具类
 */

public class UUIDUtils {
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
