package com.tradekraftcollective.microservice.persistence.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by brandonfeist on 10/8/17.
 */
@Entity
@Data
@Table(name = "artist_events")
@Cacheable(value = false)
public class ArtistEvents {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "event_id")
    private Long eventId;
}
