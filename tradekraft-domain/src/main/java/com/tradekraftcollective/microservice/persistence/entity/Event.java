package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.JsonObject;
import com.tradekraftcollective.microservice.strategy.ImageSize;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Entity
@Data
@Table(name = "events")
public class Event {

    public static final String EVENT_IMAGE_UPLOAD_PATH = "uploads/event/image/";

    public List<ImageSize> getImageSizes() {
        List<ImageSize> imageSizes = new ArrayList<>();
        imageSizes.add(new ImageSize("original", 500, null));

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

    @JsonIgnore
    public String getImageName() {
        return image;
    }

    public String getImage() {
        JsonObject jsonObject = new JsonObject();

        for (ImageSize imageSize : getImageSizes()) {
            if (imageSize.getSizeName().equals("original")) {
                jsonObject.addProperty(imageSize.getSizeName(),
                        (EVENT_AWS_URL + slug + "/" + image));
            } else {
                jsonObject.addProperty(imageSize.getSizeName(),
                        (EVENT_AWS_URL + slug + "/" + imageSize.getSizeName() + "_" + image));
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
