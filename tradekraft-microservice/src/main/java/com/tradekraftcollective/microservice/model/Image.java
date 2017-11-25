package com.tradekraftcollective.microservice.model;

import lombok.Data;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

@Data
public class Image extends JdkSerializationRedisSerializer implements Serializable {
    private Integer height;
    private String url;
    private Integer width;
}
