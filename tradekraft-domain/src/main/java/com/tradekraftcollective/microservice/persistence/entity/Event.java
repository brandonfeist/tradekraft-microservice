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
 * Created by brandonfeist on 9/28/17.
 */
@Entity
@Data
@Table(name = "events")
//@EqualsAndHashCode(callSuper = false, exclude={"artists"})
public class Event {

    public static final String EVENT_IMAGE_UPLOAD_PATH = "uploads/event/image/";

    @JsonIgnore
    public List<ImageSize> getImageSizes() {
        List<ImageSize> imageSizes = new ArrayList<>();
        imageSizes.add(new ImageSize("original", 1000, null));

        return imageSizes;
    }

    @Transient
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String EVENT_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/event/image/";

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

    @Column(name = "ticket_link")
    private String ticketLink;

    @Column(name = "entry_age", nullable = false)
    private String entryAge;

    @Column(name = "venue_name", nullable = false)
    private String venueName;

    @Column(name = "address")
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "start_datetime", nullable = false)
    private Date startDateTime;

    @Column(name = "end_datetime", nullable = false)
    private Date endDateTime;

    @Column(name = "official_tk_event", nullable = false)
    private boolean officialEvent;

    @Column(name = "slug")
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "artist_events",
        joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("events")
    private Set<Artist> artists;

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
                        (EVENT_AWS_URL + slug + "/" + image));
            } else {
                objectNode.put(imageSize.getSizeName(),
                        (EVENT_AWS_URL + slug + "/" + imageSize.getSizeName() + "_" + image));
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
