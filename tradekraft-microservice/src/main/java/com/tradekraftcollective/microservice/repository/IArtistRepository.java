package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by brandonfeist on 9/4/17.
 */
public interface IArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findAll();
    Artist findBySlug(@Param("slug") String slug);
}
