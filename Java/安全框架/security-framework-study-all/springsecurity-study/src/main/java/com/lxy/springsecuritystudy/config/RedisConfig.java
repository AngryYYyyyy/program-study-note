package com.lxy.springsecuritystudy.config;
import com.lxy.springsecuritystudy.utils.FastJsonJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/8/19 21:58
 * @Description：
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();

        // 配置连接工厂
        template.setConnectionFactory(connectionFactory);

        // 使用FastJsonJsonRedisSerializer来序列化和反序列化Redis的value值
        FastJsonJsonRedisSerializer<Object> serializer = new FastJsonJsonRedisSerializer<>(Object.class);

        // Redis中存储的value值采用JSON序列化
        template.setValueSerializer(serializer);

        // Redis中的key值使用StringRedisSerializer来序列化和反序列化
        template.setKeySerializer(new StringRedisSerializer());

        // 初始化RedisTemplate的一些参数设置
        template.afterPropertiesSet();

        return template;
    }
}
