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
public class SpotifyAuthorizationCodeGrantRequest extends AbstractRequest {
    protected SpotifyAuthorizationCodeGrantRequest(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public JSONObject get() throws IOException, WebApiException {
        final JSONObject jsonObject = JSONObject.fromObject(postJson());
        return jsonObject;
    }

    public static final class Builder extends AbstractRequest.Builder<Builder> {

        public Builder grantType(String grantType) {
            assert (grantType != null);
            return body("grant_type", grantType);
        }

        public Builder code(String code) {
            assert (code != null);
            return body("code", code);
        }

        public Builder redirectUri(String redirectUri) {
            assert (redirectUri != null);
            return body("redirect_uri", redirectUri);
        }

        public Builder basicAuthorizationHeader(String clientId, String clientSecret) {
            assert (clientId != null);
            assert (clientSecret != null);

            String idSecret = clientId + ":" + clientSecret;
            String idSecretEncoded = new String(Base64.encodeBase64(idSecret.getBytes()));

            return header("Authorization", "Basic " + idSecretEncoded);
        }

        public SpotifyAuthorizationCodeGrantRequest build() {
            host(SpotifyApi.DEFAULT_AUTHENTICATION_HOST);
            port(SpotifyApi.DEFAULT_AUTHENTICATION_PORT);
            scheme(SpotifyApi.DEFAULT_AUTHENTICATION_SCHEME);
            path("/api/token");

            return new SpotifyAuthorizationCodeGrantRequest(this);
        }

    }
}
