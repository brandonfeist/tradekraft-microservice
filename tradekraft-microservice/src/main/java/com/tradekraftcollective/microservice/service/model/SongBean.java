package com.tradekraftcollective.microservice.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.persistence.entity.media.AudioFile;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Data
public class SongBean {

    public SongBean() {}

    public SongBean(Song song) {
        this.id = song.getId();
        this.name = song.getName();
        this.songFile = song.getSongFile();
        this.trackNumber = song.getTrackNumber();
        this.bpm = song.getBpm();
        this.release = new ReleaseBean(song.getRelease());
        this.artists = convertArtistsToSimpleArtists(song.getArtists());
        this.genre = song.getGenre();
        this.slug = song.getSlug();
        this.createdAt = song.getCreatedAt();
        this.updatedAt = song.getUpdatedAt();
    }

    private Long id;

    private String name;

    private AudioFile songFile;

    private Integer trackNumber;

    private Integer bpm;

    @JsonIgnoreProperties({"image", "description", "releaseType", "releaseDate", "soundcloud", "spotify", "itunes", "appleMusic", "googlePlay", "amazon", "songs"})
    private ReleaseBean release;

    @JsonIgnoreProperties({"releases", "events", "songs"})
    private List<SimpleArtist> artists;

    private Genre genre;

    private String slug;

    private Date createdAt;

    private Date updatedAt;

    public Song convertToSong() {
        Song song = new Song();

        song.setId(this.id);
        song.setName(this.name);
        song.setSongFile(this.songFile);
        song.setTrackNumber(this.trackNumber);
        song.setBpm(this.bpm);
        song.setRelease(release.convertToRelease());
        song.setArtists(convertSimpleArtistsToArtists(this.artists));
        song.setGenre(this.genre);
        song.setSlug(this.slug);
        song.setCreatedAt(this.createdAt);
        song.setUpdatedAt(this.updatedAt);

        return song;

    }

    private List<SimpleArtist> convertArtistsToSimpleArtists(List<Artist> artists) {
        List<SimpleArtist> simpleArtists = null;

        if(artists.size() > 0) {
            simpleArtists = new ArrayList<>();

            for(Artist artist : artists) {
                simpleArtists.add(new SimpleArtist(artist));
            }
        }

        return simpleArtists;
    }

    private List<Artist> convertSimpleArtistsToArtists(List<SimpleArtist> simpleArtists) {
        List<Artist> artists = null;

        if(simpleArtists.size() > 0) {
            artists = new ArrayList<>();

            for (SimpleArtist simpleArtist : simpleArtists) {
                artists.add(SimpleArtist.convertToArtist(simpleArtist));
            }
        }

        return artists;
    }
}
