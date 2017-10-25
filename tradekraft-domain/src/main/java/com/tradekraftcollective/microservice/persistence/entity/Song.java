package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.strategy.AudioFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.*;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Entity
@Data
@Table(name = "songs")
//@EqualsAndHashCode(callSuper = false, exclude={"artists"})
public class Song {

    public static final String SONG_AUDIO_UPLOAD_PATH = "uploads/song/release-song/";

    @JsonIgnore
    public List<AudioFormat> getAudioFormats() {
        List<AudioFormat> audioFormats = new ArrayList<>();
        audioFormats.add(new AudioFormat("96k","libvorbis", "2", "96k", "ogg", "ogg"));
        audioFormats.add(new AudioFormat("original","aac", "2", "128k", "adts", "m4a"));

        return  audioFormats;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "song_file", nullable = false)
    private String songFile;

    @Column(name = "track_number", nullable = false)
    private Integer trackNumber;

    @Column(name = "duration")
    private String duration;

    @Column(name = "bpm")
    private Integer bpm;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;

    @JoinTable(name = "artist_songs",
            joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("songs")
    private Set<Artist> artists;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

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
