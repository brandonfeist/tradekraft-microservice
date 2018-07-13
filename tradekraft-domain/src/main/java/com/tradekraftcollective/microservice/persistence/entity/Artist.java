package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.Comparator;
import java.util.Collections;

/**
 * Created by brandonfeist on 9/4/17.
 */
@Entity
@Data
@Table(name = "artists")
@Cacheable(false)
public class Artist {
    private static Logger logger = LoggerFactory.getLogger(Artist.class);

    private static final double RELEASE_TO_APPEARS_ON_RATIO = 0.80;

    public static final String ARTIST_IMAGE_UPLOAD_PATH = "uploads/artist/image/";

    public static final String ARTIST_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/artist/image/";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_uuid")
    private Image image;

    @Column(name = "soundcloud")
    private String soundcloud;

    @Column(name = "facebook")
    private String facebook;

    @Column(name = "twitter")
    private String twitter;

    @Column(name = "instagram")
    private String instagram;

    @Column(name = "spotify")
    private String spotify;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToMany(mappedBy="artists", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Song> songs;

    @Transient
    private List<Release> artistReleases;

    @Transient
    private List<Release> appearsOn;

    @ManyToMany(mappedBy="artists")
    @OrderBy("startDateTime ASC")
    @JsonIgnoreProperties("artists")
    private List<Event> events;

    @JoinTable(name = "artist_years",
            joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "year_id", referencedColumnName = "id"))
    @OrderBy("year DESC")
    private List<Year> yearsActive;

    public void convertSongsToReleases() {

        artistReleases = new ArrayList<>();

        appearsOn = new ArrayList<>();

        if(songs != null) {
            Set<Release> releaseSet = new HashSet<>();
            for (Song song : songs) {
                releaseSet.add(song.getRelease());
            }

            for (Release release : releaseSet) {
                if (ownsRelease(release, this)) {
                    artistReleases.add(release);
                } else {
                    appearsOn.add(release);
                }
            }

            sortReleaseBeanList(artistReleases);

            sortReleaseBeanList(appearsOn);
        }
    }

    private void sortReleaseBeanList(List<Release> releases) {
        Collections.sort(releases, new Comparator<Release>() {
            @Override
            public int compare(Release lhs, Release rhs) {
                return lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
            }
        });
//      Lambda: (Release lhs, Release rhs) -> lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
    }

    private boolean ownsRelease(Release release, Artist artist) {
        int inSongs = 0;
        int totalSongs = release.getSongs().size();

        for(Song song : release.getSongs()) {
            if(song.hasArtist(artist)) {
                inSongs++;
            }
        }

        return (inSongs / totalSongs) >= RELEASE_TO_APPEARS_ON_RATIO;
    }

    @JsonIgnore
    public String getAWSKey() { return (ARTIST_IMAGE_UPLOAD_PATH + this.slug + "/"); }

    @JsonIgnore
    public String getAWSUrl() { return (ARTIST_AWS_URL + this.slug + "/"); }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Artist)) {
            return false;
        }
        Artist artist = (Artist) obj;
        return Objects.equals(id, artist.id) &&
                Objects.equals(name, artist.name) &&
                Objects.equals(description, artist.description) &&
                Objects.equals(image, artist.image) &&
                Objects.equals(soundcloud, artist.soundcloud) &&
                Objects.equals(facebook, artist.facebook) &&
                Objects.equals(instagram, artist.instagram) &&
                Objects.equals(twitter, artist.twitter) &&
                Objects.equals(spotify, artist.spotify) &&
                Objects.equals(slug, artist.slug) &&
                Objects.equals(events, artist.events) &&
                Objects.equals(yearsActive, artist.yearsActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, soundcloud, facebook,
                instagram, twitter, spotify, slug, events, yearsActive);
    }
}
