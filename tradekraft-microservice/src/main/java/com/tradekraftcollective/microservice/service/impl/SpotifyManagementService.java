package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.exception.WebApiException;
import com.tradekraftcollective.microservice.service.ISpotifyManagementService;
import com.tradekraftcollective.microservice.service.SpotifyApi;
import com.tradekraftcollective.microservice.utilities.apiRequests.authentication.SpotifyAuthorizationCodeGrantRequest;
import com.tradekraftcollective.microservice.utilities.apiRequests.authentication.SpotifyRefreshAccessTokenRequest;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by brandonfeist on 11/13/17.
 */
@Slf4j
@Service
public class SpotifyManagementService implements ISpotifyManagementService {
    private @Value("${vcap.services.spotify.credentials.client_id}")
    String clientId;

    private @Value("${vcap.services.spotify.credentials.client_secret}")
    String clientSecret;

    @Override
    public JSONObject getSpotifyAuthorizationToken(String authCode, String redirectUri) {
        final SpotifyApi api = SpotifyApi.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectURI(redirectUri)
                .build();

        final SpotifyAuthorizationCodeGrantRequest request = api.spotifyAuthorizationCodeGrant(authCode).build();

        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = request.get();
        } catch (IOException | WebApiException e) {
            log.error(e.toString());
        }

        return requestJsonObject;
    }

    @Override
    public JSONObject refreshSpotifyToken(String authorization, String refreshToken) {
        final SpotifyApi api = SpotifyApi.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .accessToken(authorization)
                .refreshToken(refreshToken)
                .build();

        final SpotifyRefreshAccessTokenRequest request = api.spotifyRefreshAccessToken().build();

        JSONObject requestJsonObject = null;
        try {
            requestJsonObject = request.get();
        } catch (IOException | WebApiException e) {
            log.error(e.toString());
        }

        return requestJsonObject;
    }
}
