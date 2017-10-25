package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by brandonfeist on 10/22/17.
 */
public interface IReleaseRepository extends JpaRepository<Release, Long> {
    List<Release> findBySlugStartingWith(@Param("slug") String slug);
}
