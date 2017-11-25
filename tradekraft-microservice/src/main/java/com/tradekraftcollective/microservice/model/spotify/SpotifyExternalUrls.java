package com.tradekraftcollective.microservice.model.spotify;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SpotifyExternalUrls extends JdkSerializationRedisSerializer implements Serializable {
    private Map<String,String> externalUrls = new HashMap<String, String>();

    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public String get(String key) {
        return externalUrls.get(key);
    }
}
