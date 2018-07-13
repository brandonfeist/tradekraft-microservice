package com.tradekraftcollective.microservice.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Data
public class EventBean {

    public EventBean() {}

    public EventBean(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.image = event.getImage();
        this.description = event.getDescription();
        this.ticketLink = event.getTicketLink();
        this.entryAge = event.getEntryAge();
        this.venueName = event.getVenueName();
        this.address = event.getAddress();
        this.city = event.getCity();
        this.state = event.getState();
        this.zip = event.getZip();
        this.country = event.getCountry();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.startDateTime = event.getStartDateTime();
        this.endDateTime = event.getEndDateTime();
        this.officialEvent = event.isOfficialEvent();
        this.slug = event.getSlug();
        this.createdAt = event.getCreatedAt();
        this.updatedAt = event.getUpdatedAt();

        setArtistBeans(event.getArtists());
    }

    private Long id;

    private String name;

    private Image image;

    private String description;

    private String ticketLink;

    private String entryAge;

    private String venueName;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String country;

    private Double latitude;

    private Double longitude;

    private Date startDateTime;

    private Date endDateTime;

    private boolean officialEvent;

    private String slug;

    private Date createdAt;

    private Date updatedAt;

    @JsonIgnoreProperties("events")
    private List<ArtistBean> artists;

    public Event convertToEvent() {

        Event event = new Event();

        event.setId(this.id);
        event.setName(this.name);
        event.setImage(this.image);
        event.setDescription(this.description);
        event.setTicketLink(this.ticketLink);
        event.setEntryAge(this.entryAge);
        event.setVenueName(this.venueName);
        event.setAddress(this.address);
        event.setCity(this.city);
        event.setState(this.state);
        event.setZip(this.zip);
        event.setCountry(this.country);
        event.setLatitude(this.latitude);
        event.setLongitude(this.longitude);
        event.setStartDateTime(this.startDateTime);
        event.setEndDateTime(this.endDateTime);
        event.setOfficialEvent(this.officialEvent);
        event.setSlug(this.slug);
        event.setCreatedAt(this.createdAt);
        event.setUpdatedAt(this.updatedAt);
        event.setArtists(convertArtistBeansToArtist());

        return event;
    }

    private void setArtistBeans(List<Artist> artists) {

        this.artists = new ArrayList<>();

        for(Artist artist : artists) {
            this.artists.add(new ArtistBean(artist));
        }
    }

    private List<Artist> convertArtistBeansToArtist() {

        List<Artist> artists = null;

        if(this.artists != null && this.artists.size() > 0) {
            artists = new ArrayList<>();

            for (ArtistBean artistBean : this.artists) {
                artists.add(artistBean.convertToArtist());
            }
        }

        return artists;
    }
}
