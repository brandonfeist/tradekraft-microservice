package com.tradekraftcollective.microservice.persistence.entity.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Entity
@Data
@Table(name = "song_files")
public class SongFile extends Audio {
    public SongFile() {}

    public SongFile(String name, String link) {
        super(name, link);

        this.name = name;
        this.link = link;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "song_id", nullable = false)
    @JsonIgnoreProperties("songFiles")
    private Song song;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "link", nullable = false)
    private String link;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof SongFile)) {
            return false;
        }
        SongFile songFile = (SongFile) obj;
        return id.equals(songFile.id) &&
                Objects.equals(name, songFile.name) &&
                Objects.equals(song, songFile.song) &&
                Objects.equals(link, songFile.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, song, link);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
