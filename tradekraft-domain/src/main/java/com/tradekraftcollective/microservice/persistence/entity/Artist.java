package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by brandonfeist on 9/4/17.
 */
@Entity
@Data
@Table(name = "artists")
public class Artist {

    public enum FileSizes {
        ORIGINAL("original", 1024, 1024),
        MEDIUM("medium", 512, 512),
        THUMB("thumb", 350, 350);

        private String sizeName;
        private int width;
        private int height;

        FileSizes(String sizeName, int width, int height) {
            this.sizeName = sizeName;
            this.width = width;
            this.height = height;
        }

        public String getSizeName() { return sizeName; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
    }

    public Artist() {}

    public Artist(String name, String description, String soundcloud, String facebook, String twitter,
                  String instagram, String spotify) {
        this.name = name;
        this.description = description;
        this.soundcloud = soundcloud;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
        this.spotify = spotify;
    }

    @Transient
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String ARTIST_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/artist/image/";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

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

    @Column(name = "slug")
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @JsonIgnore
    public String getImageName() {
        return image;
    }

    public String getImage() {
        JsonObject jsonObject = new JsonObject();

        for (FileSizes imageSize : FileSizes.values()) {
            if (imageSize != Artist.FileSizes.ORIGINAL) {
                jsonObject.addProperty(imageSize.getSizeName(),
                        (ARTIST_AWS_URL + slug + "/" + imageSize.getSizeName() + "_" + image));
            } else {
                jsonObject.addProperty(imageSize.getSizeName(),
                        (ARTIST_AWS_URL + slug + "/" + image));
            }
        }

        return jsonObject.toString();
    }

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
