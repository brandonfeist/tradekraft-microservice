package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.*;
import com.tradekraftcollective.microservice.persistence.entity.media.AudioFile;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.*;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Entity
@Data
@Table(name = "songs")
@Cacheable(false)
//@JsonIdentityInfo( scope = Song.class,
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class Song {

    public static final String SONG_AUDIO_UPLOAD_PATH = "uploads/song/release-song/";

    public static final String SONG_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/song/release-song/";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_file_uuid")
    private AudioFile songFile;

    @Column(name = "track_number", nullable = false)
    private Integer trackNumber;

    @Column(name = "bpm")
    private Integer bpm;

    @ManyToOne
    @JoinColumn(name = "release_id")
    @JsonIgnoreProperties({"image", "description", "releaseType", "releaseDate", "soundcloud", "spotify", "itunes", "appleMusic", "googlePlay", "amazon", "songs"})
    private Release release;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"song", "videoFiles", "videoThumbnails"})
    private List<Video> videos;

    @NotEmpty
    @JoinTable(name = "artist_songs",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
    @JsonIgnoreProperties({"artistReleases", "appearsOn", "events", "songs"})
    private List<Artist> artists;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "slug", unique = true)
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

    @JsonIgnore
    public String getAWSKey() { return (SONG_AUDIO_UPLOAD_PATH + this.slug + "/"); }

    @JsonIgnore
    public String getAWSUrl() { return (SONG_AWS_URL + this.slug + "/"); }

    @JsonIgnore
    boolean hasArtist(Artist artist) {
        return this.artists.contains(artist);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Song)) {
            return false;
        }
        Song song = (Song) obj;
        return Objects.equals(id, song.id) &&
                Objects.equals(trackNumber, song.trackNumber) &&
                Objects.equals(bpm, song.bpm) &&
                Objects.equals(name, song.name) &&
                Objects.equals(songFile, song.songFile) &&
                Objects.equals(videos, song.videos) &&
                Objects.equals(release, song.release) &&
                Objects.equals(artists, song.artists) &&
                Objects.equals(genre, song.genre) &&
                Objects.equals(slug, song.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trackNumber, bpm, name, songFile, videos, release, artists, genre, slug);
    }
}
