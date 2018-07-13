package com.tradekraftcollective.microservice.persistence.entity.media;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.persistence.entity.Video;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Entity
@Data
@Table(name = "video_files")
public class VideoFile {
    public VideoFile() {}

    public VideoFile(String name, String link) {
        this.name = name;
        this.link = link;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    @JsonIgnoreProperties("videoFiles")
    private Video video;

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
        if (!(obj instanceof VideoFile)) {
            return false;
        }
        VideoFile videoFile = (VideoFile) obj;
        return id.equals(videoFile.id) &&
                Objects.equals(name, videoFile.name) &&
                Objects.equals(video, videoFile.video) &&
                Objects.equals(link, videoFile.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, video, link);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
