package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.service.impl.HttpManager;
import com.tradekraftcollective.microservice.utilities.UtilProtos.Url.Scheme;
import com.tradekraftcollective.microservice.utilities.apiRequests.AbstractRequest;
import com.tradekraftcollective.microservice.utilities.apiRequests.SpotifyAlbumRequest;
import com.tradekraftcollective.microservice.utilities.apiRequests.authentication.SpotifyAuthorizationCodeGrantRequest;
import com.tradekraftcollective.microservice.utilities.apiRequests.authentication.SpotifyClientCredentialsGrantRequest;
import com.tradekraftcollective.microservice.utilities.apiRequests.authentication.SpotifyRefreshAccessTokenRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by brandonfeist on 11/16/17.
 */
@Slf4j
public class SpotifyApi {
    public static final String DEFAULT_HOST = "api.spotify.com";

    public static final int DEFAULT_PORT = 443;

    public static final IHttpManager DEFAULT_HTTP_MANAGER = HttpManager.builder().build();

    public static final Scheme DEFAULT_SCHEME = Scheme.HTTPS;

    public static final String DEFAULT_AUTHENTICATION_HOST = "accounts.spotify.com";

    public static final int DEFAULT_AUTHENTICATION_PORT = 443;

    public static final Scheme DEFAULT_AUTHENTICATION_SCHEME = Scheme.HTTPS;

    public static final SpotifyApi DEFAULT_API = SpotifyApi.builder().build();

    private HttpManager httpManager = null;
    private Scheme scheme;
    private int port;
    private String host;
    private String accessToken;
    private String refreshToken;
    private final String clientId;
    private final String clientSecret;
    private final String redirectURI;

    private SpotifyApi(Builder builder) {
        assert (builder.host != null);
        assert (builder.port > 0);
        assert (builder.scheme != null);


        if (builder.httpManager == null) {
            this.httpManager = HttpManager
                    .builder()
                    .build();
        } else {
            this.httpManager = builder.httpManager;
        }
        scheme = builder.scheme;
        host = builder.host;
        port = builder.port;
        accessToken = builder.accessToken;
        refreshToken = builder.refreshToken;
        clientId = builder.clientId;
        clientSecret = builder.clientSecret;
        redirectURI = builder.redirectURI;
    }

    public static Builder builder() {
        return new Builder();
    }

    public SpotifyClientCredentialsGrantRequest.Builder spotifyClientCredentialsGrant() {
        log.info("Building request to get grant Spotify client credentials.");

        SpotifyClientCredentialsGrantRequest.Builder builder = SpotifyClientCredentialsGrantRequest.builder();
        setDefaults(builder);
        builder.grantType("client_credentials");
        builder.basicAuthorizationHeader(clientId, clientSecret);
        return builder;
    }

    public SpotifyAuthorizationCodeGrantRequest.Builder spotifyAuthorizationCodeGrant(String code) {
        log.info("Building request to get Spotify authorization token.");

        SpotifyAuthorizationCodeGrantRequest.Builder builder = SpotifyAuthorizationCodeGrantRequest.builder();
        setDefaults(builder);
        builder.grantType("authorization_code");
        builder.basicAuthorizationHeader(clientId, clientSecret);
        builder.code(code);
        builder.redirectUri(redirectURI);
        return builder;
    }

    public SpotifyRefreshAccessTokenRequest.Builder spotifyRefreshAccessToken() {
        SpotifyRefreshAccessTokenRequest.Builder builder = SpotifyRefreshAccessTokenRequest.builder();
        setDefaults(builder);
        builder.grantType("refresh_token");
        builder.refreshToken(refreshToken);
        builder.basicAuthorizationHeader(clientId, clientSecret);
        return builder;
    }

    public SpotifyAlbumRequest.Builder getAlbum(String id) {
        SpotifyAlbumRequest.Builder builder = SpotifyAlbumRequest.builder();
        setDefaults(builder);
        builder.id(id);
        return builder;
    }

    private void setDefaults(AbstractRequest.Builder builder) {
        builder.httpManager(httpManager);
        builder.scheme(scheme);
        builder.host(host);
        builder.port(port);
        if (accessToken != null) {
            builder.header("Authorization", "Bearer " + accessToken);
        }
    }

    public static class Builder {

        private String host = DEFAULT_HOST;
        private int port = DEFAULT_PORT;
        private HttpManager httpManager = null;
        private Scheme scheme = DEFAULT_SCHEME;
        private String accessToken;
        private String redirectURI;
        private String clientId;
        private String clientSecret;
        private String refreshToken;

        public Builder scheme(Scheme scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder httpManager(HttpManager httpManager) {
            this.httpManager = httpManager;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder clientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder redirectURI(String redirectURI) {
            this.redirectURI = redirectURI;
            return this;
        }

        public SpotifyApi build() {
            assert (host != null);
            assert (port > 0);
            assert (scheme != null);

            return new SpotifyApi(this);
        }
    }
}
