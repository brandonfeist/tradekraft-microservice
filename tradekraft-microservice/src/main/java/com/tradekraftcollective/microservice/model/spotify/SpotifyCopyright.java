package com.tradekraftcollective.microservice.model.spotify;

import lombok.Data;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

@Data
public class SpotifyCopyright extends JdkSerializationRedisSerializer implements Serializable {
    private String text;
    private String type;
}
