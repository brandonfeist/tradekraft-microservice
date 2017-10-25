package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tradekraftcollective.microservice.strategy.ImageSize;
import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Created by brandonfeist on 9/4/17.
 */
@Entity
@Data
@Table(name = "artists")
@EqualsAndHashCode(callSuper = false, exclude={"songs", "events"})
public class Artist {

    public static final String ARTIST_IMAGE_UPLOAD_PATH = "uploads/artist/image/";

    @JsonIgnore
    public List<ImageSize> getImageSizes() {
        List<ImageSize> imageSizes = new ArrayList<>();
        imageSizes.add(new ImageSize("original", 1024, 1024));
        imageSizes.add(new ImageSize("medium", 512, 512));
        imageSizes.add(new ImageSize("thumb", 350, 350));

        return imageSizes;
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

    @ManyToMany(mappedBy="artists")
    @JsonIgnoreProperties("artists")
    private Set<Song> songs;

    @ManyToMany(mappedBy="artists")
    @JsonIgnoreProperties("artists")
    @OrderBy("startDateTime ASC")
    private Set<Event> events;

    @JsonIgnore
    public String getImageName() {
        return image;
    }

    public ObjectNode getImage() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        for (ImageSize imageSize : getImageSizes()) {
            if (imageSize.getSizeName().equals("original")) {
                objectNode.put(imageSize.getSizeName(),
                        (ARTIST_AWS_URL + slug + "/" + image));
            } else {
                objectNode.put(imageSize.getSizeName(),
                        (ARTIST_AWS_URL + slug + "/" + imageSize.getSizeName() + "_" + image));
            }
        }

        return objectNode;
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
