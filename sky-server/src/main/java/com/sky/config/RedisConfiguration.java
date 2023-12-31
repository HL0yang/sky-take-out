package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){

        log.info("开始创建redis对象");
        RedisTemplate redisTemplate = new RedisTemplate();
        //建立连接
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //为key设置序列化器，默认为JdkSerializationRedisSerializer()
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        return redisTemplate;

    }
}
