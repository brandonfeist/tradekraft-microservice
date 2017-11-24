package com.tradekraftcollective.microservice.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by brandonfeist on 10/9/17.
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfiguration {

    private @Value("${vcap.services.redis.credentials.host}")
    String redisHost;

    private @Value("${vcap.services.redis.credentials.port}")
    int redisPort;

    private @Value("${vcap.services.redis.credentials.password}")
    String redisPassword;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        log.info("Getting jedis connection factory.");

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHost);
        factory.setPort(redisPort);
        factory.setPassword(redisPassword);
        factory.setUsePool(true);

        return factory;
    }

    @Bean
    public RedisSerializer redisStringSerializer() {
        log.info("Setting up redis serializer.");

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        return stringRedisSerializer;
    }

    @Bean(name="redisTemplate")
    RedisTemplate<Object, Object> redisTemplate() {
        log.info("Getting redis template.");

        final RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());

        return template;
    }

    @Bean
    CacheManager cacheManager() {
        log.info("Setting up redis cache manager.");

        return new RedisCacheManager(redisTemplate());
    }
}
