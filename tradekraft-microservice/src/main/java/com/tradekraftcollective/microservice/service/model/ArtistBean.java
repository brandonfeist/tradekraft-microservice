package com.tradekraftcollective.microservice.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.*;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

@Slf4j
@Data
public class ArtistBean {

    public static final double RELEASE_TO_APPEARS_ON_RATIO = 0.80;

    public ArtistBean() {}

    public ArtistBean(Artist artist) {
        this.id = artist.getId();
        this.name = artist.getName();
        this.description = artist.getDescription();
        this.image = artist.getImage();
        this.soundcloud = artist.getSoundcloud();
        this.facebook = artist.getFacebook();
        this.instagram = artist.getInstagram();
        this.spotify = artist.getSpotify();
        this.slug = artist.getSlug();
        this.yearsActive = artist.getYearsActive();
        this.createdAt = artist.getCreatedAt();
        this.updatedAt = artist.getUpdatedAt();

        setEventBeans(artist.getEvents());
        convertSongsToReleases(artist.getSongs(), artist);
    }

    private Long id;

    private String name;

    private String description;

    private Image image;

    private String soundcloud;

    private String facebook;

    private String twitter;

    private String instagram;

    private String spotify;

    private String slug;

    private Date createdAt;

    private Date updatedAt;

    private List<Year> yearsActive;

    private List<ReleaseBean> artistReleases;

    private List<ReleaseBean> appearsOn;

    @JsonIgnoreProperties("artists")
    private List<EventBean> events;

    @JsonIgnore
    public Artist convertToArtist() {

        Artist artist = new Artist();

        artist.setId(this.id);
        artist.setName(this.name);
        artist.setDescription(this.description);
        artist.setImage(this.image);
        artist.setSoundcloud(this.soundcloud);
        artist.setFacebook(this.facebook);
        artist.setTwitter(this.twitter);
        artist.setInstagram(this.instagram);
        artist.setSpotify(this.spotify);
        artist.setSlug(this.slug);
        artist.setCreatedAt(this.createdAt);
        artist.setUpdatedAt(this.updatedAt);
        artist.setYearsActive(this.yearsActive);
        artist.setEvents(convertEventBeansToEvents());
        artist.setSongs(getSongsFromReleases());

        return artist;
    }


    private List<Song> getSongsFromReleases() {
        List<Song> returnSongs = new ArrayList<>();
        List<ReleaseBean> releases = new ArrayList<>();

        if(artistReleases != null) {
            releases.addAll(artistReleases);
        }

        if(appearsOn != null) {
            releases.addAll(appearsOn);
        }

        // This is trash.
        for(ReleaseBean release : releases) {
            for(SongBean songBean : release.getSongs()) {
                for(SimpleArtist simpleArtist : songBean.getArtists()) {
                    if(simpleArtist.getSlug().equals(this.slug)) {
                        returnSongs.add(songBean.convertToSong());
                    }
                }
            }
        }

        if(returnSongs.size() > 0) {
            return returnSongs;
        }

        return null;
    }

    private void convertSongsToReleases(List<Song> songs, Artist artist) {

        artistReleases = new ArrayList<>();

        appearsOn = new ArrayList<>();

        if(songs != null) {
            Set<Release> releaseSet = new HashSet<>();
            for (Song song : songs) {
                releaseSet.add(song.getRelease());
            }

            for (Release release : releaseSet) {
                if (ownsRelease(release, artist)) {
                    artistReleases.add(new ReleaseBean(release));
                } else {
                    appearsOn.add(new ReleaseBean(release));
                }
            }

            sortReleaseBeanList(artistReleases);

            sortReleaseBeanList(appearsOn);
        }
    }

    private void sortReleaseBeanList(List<ReleaseBean> releases) {
        Collections.sort(releases, new Comparator<ReleaseBean>() {
            @Override
            public int compare(ReleaseBean lhs, ReleaseBean rhs) {
                return lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
            }
        });
//      Lambda: (Release lhs, Release rhs) -> lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
    }

    private boolean ownsRelease(Release release, Artist artist) {
        int inSongs = 0;
        int totalSongs = release.getSongs().size();

        for(Song song : release.getSongs()) {
            if(song.getArtists().contains(artist)) {
                inSongs++;
            }
        }

        return (inSongs / totalSongs) >= RELEASE_TO_APPEARS_ON_RATIO;
    }

    private void setEventBeans(List<Event> events) {

        if(events != null) {
            this.events = new ArrayList<>();

            for (Event event : events) {
                this.events.add(new EventBean(event));
            }
        }
    }

    private List<Event> convertEventBeansToEvents() {

        List<Event> events = null;

        if(this.events != null && this.events.size() > 0) {
            events = new ArrayList<>();

            for (EventBean eventBean : this.events) {
                events.add(eventBean.convertToEvent());
            }
        }

        return events;
    }

}
