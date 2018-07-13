package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tradekraftcollective.microservice.persistence.entity.media.VideoFile;
import com.tradekraftcollective.microservice.persistence.entity.media.VideoThumbnail;
import com.tradekraftcollective.microservice.strategy.VideoFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name = "videos")
@Cacheable(false)
public class Video {

    public static final String VIDEO_UPLOAD_PATH = "uploads/video/";

    public static final String VIDEO_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/video/";

    @JsonIgnore
    public List<VideoFormat> getVideoFormats() {
        List<VideoFormat> audioFormats = new ArrayList<>();
        audioFormats.add(new VideoFormat("mp4","libx264", "mp4", "mp4"));
//        audioFormats.add(new VideoFormat("webm","libvpx", "webm", "webm"));

        return  audioFormats;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    @MapKey(name = "name")
    @JsonIgnoreProperties("video")
    private Map<String, VideoFile> videoFiles;

    @Column(name = "video_youtube_url")
    private String youtubeUrl;

    @Column(name = "external_url")
    private String externalUrl;

    @Column(name = "featured", nullable = false)
    private boolean featured;

    @ManyToOne
    @JoinColumn(name = "song_id")
    @JsonIgnoreProperties("videos")
    private Song song;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    @MapKey(name = "name")
    @JsonIgnoreProperties("video")
    private Map<String, VideoThumbnail> videoThumbnails;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @JsonIgnore
    public String getAWSKey() { return (VIDEO_UPLOAD_PATH + this.slug + "/"); }

    @JsonIgnore
    public String getAWSUrl() { return (VIDEO_AWS_URL + this.slug + "/"); }

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
        if (!(obj instanceof Release)) {
            return false;
        }
        Video video = (Video) obj;
        return Objects.equals(id, video.id) &&
                Objects.equals(name, video.name) &&
                Objects.equals(externalUrl, video.externalUrl) &&
                Objects.equals(videoFiles, video.videoFiles) &&
                Objects.equals(videoThumbnails, video.videoThumbnails) &&
                Objects.equals(featured, video.featured) &&
                Objects.equals(slug, video.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, externalUrl, videoFiles, videoThumbnails, featured, slug);
    }
}
