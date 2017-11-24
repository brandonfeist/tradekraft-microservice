package com.tradekraftcollective.microservice.model.spotify;

import lombok.Data;

@Data
public class SpotifySimpleArtist {
    private SpotifyExternalUrls externalUrls;
    private String href;
    private String id;
    private String name;
    private SpotifyEntityType type = SpotifyEntityType.ARTIST;
    private String uri;
}
