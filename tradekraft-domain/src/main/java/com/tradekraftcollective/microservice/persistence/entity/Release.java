package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tradekraftcollective.microservice.strategy.ImageSize;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Entity
@Data
@Table(name = "releases")
public class Release {

    public static final String RELEASE_IMAGE_UPLOAD_PATH = "uploads/release/image/";

    @JsonIgnore
    public List<ImageSize> getImageSizes() {
        List<ImageSize> imageSizes = new ArrayList<>();
        imageSizes.add(new ImageSize("original", 1024, 1024));
        imageSizes.add(new ImageSize("medium", 512, 512));
        imageSizes.add(new ImageSize("thumb", 350, 350));

        return imageSizes;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

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
    private Set<Song> songs;

    @Column(name = "slug")
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
