package com.tradekraftcollective.microservice.model.spotify;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SpotifyExternalIds extends JdkSerializationRedisSerializer implements Serializable {
    private final Map<String,String> externalIds = new HashMap<String,String>();

    public Map<String,String> getExternalIds() {
        return externalIds;
    }
}
