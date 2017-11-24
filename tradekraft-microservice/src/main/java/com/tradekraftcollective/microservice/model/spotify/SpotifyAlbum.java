package com.tradekraftcollective.microservice.model.spotify;

import com.tradekraftcollective.microservice.model.Image;
import lombok.Data;

import java.util.List;

@Data
public class SpotifyAlbum {
    private SpotifyAlbumType albumType;
    private List<SpotifySimpleArtist> artists;
    private List<String> availableMarkets;
    private List<SpotifyCopyright> copyrights;
    private SpotifyExternalIds externalIds;
    private SpotifyExternalUrls externalUrls;
    private List<String> genres;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private int popularity;
    private String releaseDate;
    private String releaseDatePrecision;
    private SpotifyPage<SpotifySimpleTrack> tracks;
    private SpotifyEntityType type = SpotifyEntityType.ALBUM;
    private String uri;
}
