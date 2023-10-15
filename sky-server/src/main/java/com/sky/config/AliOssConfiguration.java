package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，在springboot程序启动时，将创建的阿里云Utils类注入到IOC容器中
 */
@Configuration
@Slf4j
public class AliOssConfiguration {

    @Bean
    @ConditionalOnMissingBean //soringboot项目启动时，排除多余的工具类
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){

        log.info("创建阿里云OSS工具类对象...");

        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());

    }
}
