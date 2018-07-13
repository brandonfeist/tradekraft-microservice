package com.tradekraftcollective.microservice.persistence.entity.media;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Entity
@Data
@Table(name = "images")
public class Image {

    public Image() {}

    public Image(UUID uuid, String link) {

        this.uuid = uuid.toString();

        this.link = link;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @NotBlank
    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid.toString();
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
        if (!(obj instanceof Image)) {
            return false;
        }
        Image image = (Image) obj;
        return Objects.equals(id, image.id) &&
                Objects.equals(uuid, image.uuid) &&
                Objects.equals(link, image.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, link);
    }
}
