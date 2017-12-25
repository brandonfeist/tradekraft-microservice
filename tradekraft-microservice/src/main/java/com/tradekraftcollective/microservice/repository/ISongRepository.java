package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by brandonfeist on 10/22/17.
 */
public interface ISongRepository extends JpaRepository<Song, Long> {
    List<Song> findBySlugStartingWith(@Param("slug") String slug);

    Song findBySlug(@Param("slug") String slug);
}
