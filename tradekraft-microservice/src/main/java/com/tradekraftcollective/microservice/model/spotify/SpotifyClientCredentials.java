package com.tradekraftcollective.microservice.model.spotify;

import lombok.Data;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

@Data
public class SpotifyClientCredentials extends JdkSerializationRedisSerializer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String tokenType;
    private int expiresIn;
}
