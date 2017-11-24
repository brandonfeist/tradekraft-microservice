package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.exception.WebApiException;
import com.tradekraftcollective.microservice.model.spotify.SpotifyAlbum;
import com.tradekraftcollective.microservice.model.spotify.SpotifyClientCredentials;
import com.tradekraftcollective.microservice.service.CacheService;
import com.tradekraftcollective.microservice.service.ISpotifyManagementService;
import com.tradekraftcollective.microservice.service.SpotifyApi;
import com.tradekraftcollective.microservice.utilities.apiRequests.SpotifyAlbumRequest;
import com.tradekraftcollective.microservice.utilities.apiRequests.authentication.SpotifyAuthorizationCodeGrantRequest;
import com.tradekraftcollective.microservice.utilities.apiRequests.authentication.SpotifyClientCredentialsGrantRequest;
import com.tradekraftcollective.microservice.utilities.apiRequests.authentication.SpotifyRefreshAccessTokenRequest;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    private CacheService cacheService;

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

    @Cacheable(value = "spotify-client-token")
    private SpotifyClientCredentials getSpotifyClientAuthorizationToken() {
        if(cacheService.getSpotifyToken().size() < 1) {
            log.info("Retrieving Spotify authorization for client.");

            final SpotifyApi api = SpotifyApi.builder()
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .build();

            final SpotifyClientCredentialsGrantRequest request = api.spotifyClientCredentialsGrant().build();

            try {
                SpotifyClientCredentials requestReturn = request.get();
                cacheService.saveSpotifyToken(requestReturn);
                return requestReturn;
            } catch (IOException | WebApiException e) {
                log.error(e.toString());
            }
        } else {
            return cacheService.getSpotifyToken().get(0);
        }

        return null;
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

    @Override
    public SpotifyAlbum getSpotifyAlbumInformation(String albumId) {
        log.info("Getting Spotify album with ID: [{}]", albumId);

        final SpotifyClientCredentials authorization = getSpotifyClientAuthorizationToken();

        final SpotifyApi api = SpotifyApi.builder()
                .accessToken(authorization.getAccessToken())
                .build();

        final SpotifyAlbumRequest request = api.getAlbum(albumId).build();

        try {
            return request.get();
        } catch(IOException | WebApiException e) {
            log.error(e.toString());
        }

        return null;
    }
}
