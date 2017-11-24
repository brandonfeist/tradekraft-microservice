package com.tradekraftcollective.microservice.model.spotify;

public enum SpotifyAlbumType {
    ALBUM("album"),
    SINGLE("single"),
    COMPILATION("compilation");

    public final String type;

    SpotifyAlbumType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
