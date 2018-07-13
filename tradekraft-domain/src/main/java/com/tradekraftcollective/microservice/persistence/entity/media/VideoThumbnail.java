package com.tradekraftcollective.microservice.persistence.entity.media;

import com.tradekraftcollective.microservice.persistence.entity.Video;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;

@Slf4j
@Entity
@Data
@Table(name = "video_thumbnails")
public class VideoThumbnail extends BaseImage {
    public VideoThumbnail() {}

    public VideoThumbnail(String name, String link, Integer width, Integer height) {
        super(name, link, width, height);

        this.name = name;
        this.link = link;
        this.width = width;
        this.height = height;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "link", nullable = false)
    private String link;

    @NotBlank
    @Column(name = "width", nullable = false)
    private Integer width;

    @NotBlank
    @Column(name = "height", nullable = false)
    private Integer height;

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
