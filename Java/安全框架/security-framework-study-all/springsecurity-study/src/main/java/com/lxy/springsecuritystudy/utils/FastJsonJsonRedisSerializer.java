package com.lxy.springsecuritystudy.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import java.nio.charset.StandardCharsets;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/19 22:00
 * @Description：Redis使用FastJson进行序列化
 */
public class FastJsonJsonRedisSerializer<T> implements RedisSerializer<T> {
    private final Class<T> clazz;

    public FastJsonJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, StandardCharsets.UTF_8);
        return JSON.parseObject(str, clazz, JSONReader.Feature.SupportAutoType);
    }
}

