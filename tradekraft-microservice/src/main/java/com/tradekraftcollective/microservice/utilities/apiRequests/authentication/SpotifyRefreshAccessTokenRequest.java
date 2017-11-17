package com.tradekraftcollective.microservice.utilities.apiRequests.authentication;

import com.tradekraftcollective.microservice.exception.WebApiException;
import com.tradekraftcollective.microservice.service.SpotifyApi;
import com.tradekraftcollective.microservice.utilities.apiRequests.AbstractRequest;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

/**
 * Created by brandonfeist on 11/16/17.
 */
public class SpotifyRefreshAccessTokenRequest extends AbstractRequest {
    protected SpotifyRefreshAccessTokenRequest(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public JSONObject get() throws IOException, WebApiException {
        JSONObject jsonObject = JSONObject.fromObject(postJson());
        return jsonObject;
    }

    public static final class Builder extends AbstractRequest.Builder<Builder> {

        public Builder basicAuthorizationHeader(String clientId, String clientSecret) {
            assert (clientId != null);
            assert (clientSecret != null);

            String idSecret = clientId + ":" + clientSecret;
            String idSecretEncoded = new String(Base64.encodeBase64(idSecret.getBytes()));

            return header("Authorization", "Basic " + idSecretEncoded);
        }

        public Builder grantType(String grantType) {
            assert (grantType != null);
            return body("grant_type", grantType);
        }

        public Builder refreshToken(String refreshToken) {
            assert (refreshToken != null);
            return body("refresh_token", refreshToken);
        }

        public SpotifyRefreshAccessTokenRequest build() {
            host(SpotifyApi.DEFAULT_AUTHENTICATION_HOST);
            port(SpotifyApi.DEFAULT_AUTHENTICATION_PORT);
            scheme(SpotifyApi.DEFAULT_AUTHENTICATION_SCHEME);
            path("/api/token");

            return new SpotifyRefreshAccessTokenRequest(this);
        }
    }
}
