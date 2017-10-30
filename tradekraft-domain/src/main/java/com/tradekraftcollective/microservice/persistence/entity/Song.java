package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tradekraftcollective.microservice.strategy.AudioFormat;
import com.tradekraftcollective.microservice.strategy.ImageSize;
import lombok.*;

import javax.persistence.*;
import java.util.*;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Entity
@Data
@Table(name = "songs")
@EqualsAndHashCode(callSuper = false, exclude={"artists", "release"})
public class Song {

    public static final String SONG_AUDIO_UPLOAD_PATH = "uploads/song/release-song/";

    @JsonIgnore
    public List<AudioFormat> getAudioFormats() {
        List<AudioFormat> audioFormats = new ArrayList<>();
        audioFormats.add(new AudioFormat("96k","libvorbis", "2", "96k", "ogg", "ogg"));
        audioFormats.add(new AudioFormat("128k","aac", "2", "128k", "adts", "m4a"));
//        audioFormats.add(new AudioFormat("original","libmp3lame", "2", "192k", "mp3", "mp3"));

        return  audioFormats;
    }

    @Transient
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static final String SONG_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/song/release-song/";

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

    @ManyToOne(fetch=FetchType.LAZY)
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

    @JsonIgnore
    public String getSongFileName() {
        return songFile;
    }

    public ObjectNode getSongFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        for (AudioFormat audioFormat : getAudioFormats()) {
            if (audioFormat.getFileName().equals("original")) {
                objectNode.put(audioFormat.getExtension(),
                        (SONG_AWS_URL + slug + "/" + songFile + "." + audioFormat.getExtension()));
            } else {
                objectNode.put(audioFormat.getExtension(),
                        (SONG_AWS_URL + slug + "/" + audioFormat.getFileName() + "_" + songFile + "." + audioFormat.getExtension()));
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
