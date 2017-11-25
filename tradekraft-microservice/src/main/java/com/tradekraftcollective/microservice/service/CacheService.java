package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.model.spotify.SpotifyClientCredentials;

import java.util.List;

public interface CacheService {
    public void saveSpotifyToken(SpotifyClientCredentials token);

    public SpotifyClientCredentials getSpotifyToken();
}
