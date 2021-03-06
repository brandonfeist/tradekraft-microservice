package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.*;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Entity
@Data
@Table(name = "releases")
@Cacheable(false)
public class Release {

    public static final String RELEASE_IMAGE_UPLOAD_PATH = "uploads/release/image/";

    public static final String RELEASE_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/release/image/";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_uuid")
    private Image image;

    @Column(name = "description")
    private String description;

    @Column(name = "release_type")
    private String releaseType;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "soundcloud")
    private String soundcloud;

    @Column(name = "spotify")
    private String spotify;

    @Column(name = "itunes")
    private String itunes;

    @Column(name = "apple_music")
    private String appleMusic;

    @Column(name = "google_play")
    private String googlePlay;

    @Column(name = "amazon")
    private String amazon;

    @OneToMany(mappedBy = "release", cascade = CascadeType.ALL)
    @OrderBy("trackNumber ASC")
    @JsonIgnoreProperties("release")
    private List<Song> songs;

    @Column(name = "free_release", nullable = false)
    private boolean freeRelease;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @JsonIgnore
    public String getAWSKey() { return (RELEASE_IMAGE_UPLOAD_PATH + this.slug + "/"); }

    @JsonIgnore
    public String getAWSUrl() { return (RELEASE_AWS_URL + this.slug + "/"); }

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
        if (!(obj instanceof Release)) {
            return false;
        }
        Release release = (Release) obj;
        return Objects.equals(id, release.id) &&
                Objects.equals(name, release.name) &&
                Objects.equals(description, release.description) &&
                Objects.equals(image, release.image) &&
                Objects.equals(releaseType, release.releaseType) &&
                Objects.equals(releaseDate, release.releaseDate) &&
                Objects.equals(soundcloud, release.soundcloud) &&
                Objects.equals(spotify, release.spotify) &&
                Objects.equals(itunes, release.itunes) &&
                Objects.equals(appleMusic, release.appleMusic) &&
                Objects.equals(googlePlay, release.googlePlay) &&
                Objects.equals(amazon, release.amazon) &&
                Objects.equals(slug, release.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image, releaseType, releaseDate,
                soundcloud, spotify, itunes, appleMusic, googlePlay, appleMusic,
                slug);
    }
}
