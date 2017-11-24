package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.model.spotify.SpotifyClientCredentials;
import com.tradekraftcollective.microservice.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("cacheService")
public class RedisService implements CacheService {
    @Resource(name = "redisTemplate")
    private ListOperations<String, SpotifyClientCredentials> spotifyTokenList;

    @Resource(name = "redisTemplate")
    private RedisOperations<String, String> spotifyTokenExpiration;


    @Override
    public void saveSpotifyToken(SpotifyClientCredentials token) {
        log.info("Saving spotify client access token to redis.");

        spotifyTokenList.leftPush("spotifyToken", token);

        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Date date = Date.from(zonedDateTime.plus(token.getExpiresIn() - 5, ChronoUnit.MINUTES).toInstant());
        spotifyTokenExpiration.expireAt("spotifyToken", date);
    }

    @Override
    public List<SpotifyClientCredentials> getSpotifyToken() {
        return spotifyTokenList.range("spotifyToken", 0, -1);
    }
}
