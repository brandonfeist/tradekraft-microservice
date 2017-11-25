package com.tradekraftcollective.microservice.model.spotify;

import lombok.Data;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

@Data
public class SpotifySimpleArtist extends JdkSerializationRedisSerializer implements Serializable {
    private SpotifyExternalUrls externalUrls;
    private String href;
    private String id;
    private String name;
    private SpotifyEntityType type = SpotifyEntityType.ARTIST;
    private String uri;
}
