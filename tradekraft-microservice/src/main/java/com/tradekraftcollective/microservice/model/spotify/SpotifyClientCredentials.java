package com.tradekraftcollective.microservice.model.spotify;

import lombok.Data;

@Data
public class SpotifyClientCredentials {
    private String accessToken;
    private String tokenType;
    private int expiresIn;
}
