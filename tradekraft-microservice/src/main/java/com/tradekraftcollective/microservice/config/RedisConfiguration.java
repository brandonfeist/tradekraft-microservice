package com.tradekraftcollective.microservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by brandonfeist on 10/9/17.
 */
@Configuration
public class RedisConfiguration {
    private final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    private @Value("${vcap.services.redis.credentials.host}")
    String redisHost;

    private @Value("${vcap.services.redis.credentials.port}")
    int redisPort;

    private @Value("${vcap.services.redis.credentials.password}")
    String redisPassword;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        logger.info("Getting jedis connection factory.");

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(redisHost);
        factory.setPort(redisPort);
        factory.setPassword(redisPassword);
        factory.setUsePool(true);

        return factory;
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        logger.info("Getting redis template.");

        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));

        return template;
    }
}
