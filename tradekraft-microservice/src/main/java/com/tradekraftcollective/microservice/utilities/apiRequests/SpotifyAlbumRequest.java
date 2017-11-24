package com.tradekraftcollective.microservice.utilities.apiRequests;

import com.tradekraftcollective.microservice.exception.WebApiException;
import com.tradekraftcollective.microservice.model.spotify.SpotifyAlbum;
import com.tradekraftcollective.microservice.utilities.SpotifyJsonUtil;

import java.io.IOException;

public class SpotifyAlbumRequest extends AbstractRequest {
    public SpotifyAlbumRequest(Builder builder) {
        super(builder);
    }

    public SpotifyAlbum get() throws IOException, WebApiException {
        return SpotifyJsonUtil.createAlbum(getJson());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends AbstractRequest.Builder<Builder> {

        /**
         * The album with the given id.
         *
         * @param id The id for the album.
         * @return AlbumRequest
         */
        public Builder id(String id) {
            assert (id != null);
            return path(String.format("/v1/albums/%s", id));
        }

        public SpotifyAlbumRequest build() {
            return new SpotifyAlbumRequest(this);
        }

    }
}
