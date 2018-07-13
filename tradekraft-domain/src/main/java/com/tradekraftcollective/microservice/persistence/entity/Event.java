package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.*;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Entity
@Data
@Table(name = "events")
@Cacheable(false)
public class Event {

    public static final String EVENT_IMAGE_UPLOAD_PATH = "uploads/event/image/";

    @Transient
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String EVENT_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/event/image/";

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

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToMany
    @JoinTable(name = "artist_events",
        joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("events")
    private List<Artist> artists;

    @JsonIgnore
    public String getAWSKey() { return (EVENT_IMAGE_UPLOAD_PATH + this.slug + "/"); }

    @JsonIgnore
    public String getAWSUrl() { return (EVENT_AWS_URL + this.slug + "/"); }

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
        if (!(obj instanceof Event)) {
            return false;
        }
        Event event = (Event) obj;
        return Objects.equals(id, event.id) &&
                officialEvent == officialEvent &&
                Double.doubleToLongBits(latitude) == Double.doubleToLongBits(event.latitude) &&
                Double.doubleToLongBits(longitude) == Double.doubleToLongBits(event.longitude) &&
                Objects.equals(name, event.name) &&
                Objects.equals(description, event.description) &&
                Objects.equals(image, event.image) &&
                Objects.equals(ticketLink, event.ticketLink) &&
                Objects.equals(entryAge, event.entryAge) &&
                Objects.equals(venueName, event.venueName) &&
                Objects.equals(address, event.address) &&
                Objects.equals(city, event.city) &&
                Objects.equals(state, event.state) &&
                Objects.equals(zip, event.zip) &&
                Objects.equals(country, event.country) &&
                Objects.equals(startDateTime, event.startDateTime) &&
                Objects.equals(endDateTime, event.endDateTime) &&
                Objects.equals(slug, event.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, officialEvent, latitude, longitude, name, description, image,
                ticketLink, entryAge, venueName, address, city, state, zip, country, startDateTime, endDateTime,
                slug);
    }
}
