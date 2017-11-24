package com.tradekraftcollective.microservice.model.spotify;

import lombok.Data;

import java.util.List;

@Data
public class SpotifySimpleTrack {
    private List<SpotifySimpleArtist> artists;

    private List<String> availableMarkets;
    private int discNumber;
    private int duration;
    private boolean explicit;
    private SpotifyExternalUrls externalUrls;
    private String href;
    private String id;
    private String name;
    private String previewUrl;
    private int trackNumber;
    private SpotifyEntityType type = SpotifyEntityType.TRACK;
    private String uri;
}
