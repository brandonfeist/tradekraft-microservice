package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.model.spotify.SpotifyClientCredentials;
import com.tradekraftcollective.microservice.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("cacheService")
public class RedisService implements CacheService {
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, SpotifyClientCredentials> spotifyTokenTemplate;


    @Override
    public void saveSpotifyToken(SpotifyClientCredentials token) {
        log.info("Saving spotify client access token to redis.");

        BoundValueOperations<String, SpotifyClientCredentials> boundValueOperations =
                spotifyTokenTemplate.boundValueOps("spotify-client-token");
        boundValueOperations.set(token, (token.getExpiresIn() - 10), TimeUnit.SECONDS);
    }

    @Override
    public SpotifyClientCredentials getSpotifyToken() {
        return spotifyTokenTemplate.boundValueOps("spotify-client-token").get();
    }
}
