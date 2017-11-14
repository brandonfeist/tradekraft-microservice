package com.tradekraftcollective.microservice.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by brandonfeist on 11/13/17.
 */
public interface ISpotifyManagementService {
    String getSpotifyAuthorizationToken(String authCode, String redirectUri);
}
