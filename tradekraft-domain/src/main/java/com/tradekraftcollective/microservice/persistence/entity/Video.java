package com.tradekraftcollective.microservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tradekraftcollective.microservice.strategy.VideoFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "videos")
public class Video {

    public static final String VIDEO_UPLOAD_PATH = "uploads/video/";

    public static final String VIDEO_AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/video/";

    @JsonIgnore
    public List<VideoFormat> getVideoFormats() {
        List<VideoFormat> audioFormats = new ArrayList<>();
        audioFormats.add(new VideoFormat("mp4","libx264", "mp4", "mp4"));
        audioFormats.add(new VideoFormat("webm","libvpx", "webm", "webm"));

        return  audioFormats;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "video_file")
    private String videoFile;

    @Transient
    private String videoThumbnail;

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

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    // Start and end time in milliseconds
    @Transient
    private Integer videoPreviewStartTime;

    @Transient
    private Integer videoPreviewEndTime;

    public ObjectNode getVideoFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        if(videoFile != null) {
            for (VideoFormat videoFormat : getVideoFormats()) {
                objectNode.put(videoFormat.getExtension(),
                        (VIDEO_AWS_URL + slug + "/" + videoFormat.getFileName() + "_" + videoFile + "." + videoFormat.getExtension()));

                if(isFeatured()) {
                    objectNode.put(videoFormat.getExtension() + "_preview",
                            (VIDEO_AWS_URL + slug + "/" + videoFormat.getFileName() + "_" + videoFile + "_preview." + videoFormat.getExtension()));
                }
            }
        }

        return objectNode;
    }

    public String getVideoThumbnail() {
        if(videoFile != null) {
            return (VIDEO_AWS_URL + slug + "/" + videoFile + ".jpg");
        }

        return null;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Release)) {
            return false;
        }
        Video video = (Video) obj;
        return id == video.id &&
                Objects.equals(name, video.name) &&
                Objects.equals(videoFile, video.videoFile) &&
                Objects.equals(featured, video.featured) &&
                Objects.equals(song, video.song) &&
                Objects.equals(slug, video.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, videoFile, featured, song, slug);
    }
}
