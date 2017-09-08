package com.tradekraftcollective.microservice.persistence.entity;

import lombok.Data;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by brandonfeist on 9/4/17.
 */
@Entity
@Data
@Table(name = "artists")
public class Artist {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
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

    @Column(name = "status")
    private String status;

    @Column(name = "slug")
    private String slug;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
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
