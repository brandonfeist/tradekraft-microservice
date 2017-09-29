package com.tradekraftcollective.microservice.persistence.entity;

import com.tradekraftcollective.microservice.strategy.ImageSize;
import lombok.Data;

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
