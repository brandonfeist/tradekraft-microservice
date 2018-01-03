package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tradekraftcollective.microservice.strategy.ImageSize;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by brandonfeist on 9/4/17.
 */
@Entity
@Data
@Table(name = "artists")
@Cacheable(false)
public class Artist {
    private static Logger logger = LoggerFactory.getLogger(Artist.class);

    public static final String ARTIST_IMAGE_UPLOAD_PATH = "uploads/artist/image/";

    public static final String ARTIST_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/artist/image/";

    public static final double RELEASE_TO_APPEARS_ON_RATIO = 0.80;

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

    @NotBlank
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

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToMany(mappedBy="artists", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Song> songs;

    @ManyToMany(mappedBy="artists")
    @OrderBy("startDateTime ASC")
    @JsonIgnoreProperties("artists")
    private List<Event> events;

    @JoinTable(name = "artist_years",
            joinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "year_id", referencedColumnName = "id"))
    @OrderBy("year DESC")
    private List<Year> yearsActive;

    public List<Event> getEvents() {
        if(this.events != null) {
            List<Event> eventsList = this.events;
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            for (int eventIndex = (eventsList.size() - 1); eventIndex >= 0; eventIndex--) {
                if (currentTimestamp.compareTo(eventsList.get(eventIndex).getEndDateTime()) > 0) {
                    eventsList.remove(eventIndex);
                }
            }

            return eventsList;
        }

        return null;
    }

    public JsonNode getReleases() {
        if(getSongs() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();

            Set<Release> releaseSet = new HashSet<>();
            for (Song song : getSongs()) {
                releaseSet.add(song.getRelease());
            }

            List<Release> releaseList = new ArrayList<>();
            List<Release> appearsOnList = new ArrayList<>();
            for (Release release : releaseSet) {
                if (ownsRelease(release)) {
                    releaseList.add(release);
                } else {
                    appearsOnList.add(release);
                }
            }

            sortReleaseList(releaseList);
            sortReleaseList(appearsOnList);

            objectNode.set("artistReleases", objectMapper.convertValue(releaseList, JsonNode.class));
            objectNode.set("appearsOn", objectMapper.convertValue(appearsOnList, JsonNode.class));

            return objectNode;
        }

        return null;
    }

    private void sortReleaseList(List<Release> releases) {
        Collections.sort(releases, new Comparator<Release>() {
            public int compare(Release lhs, Release rhs) {
                return lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
            }
        });
//      Lambda: (Release lhs, Release rhs) -> lhs.getReleaseDate().compareTo(rhs.getReleaseDate());
    }

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

    private boolean ownsRelease(Release release) {
        int inSongs = 0;
        int totalSongs = release.getSongs().size();

        for(Song song : release.getSongs()) {
            if(song.getArtists().contains(this)) {
                inSongs++;
            }
        }

        return (inSongs / totalSongs) >= RELEASE_TO_APPEARS_ON_RATIO;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Artist)) {
            return false;
        }
        Artist artist = (Artist) obj;
        return id == artist.id &&
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
