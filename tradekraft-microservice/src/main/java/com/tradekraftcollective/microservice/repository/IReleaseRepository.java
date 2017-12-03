package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by brandonfeist on 10/22/17.
 */
public interface IReleaseRepository extends JpaRepository<Release, Long>, JpaSpecificationExecutor<Release> {
    Release findBySlug(@Param("slug") String slug);

    List<Release> findBySlugStartingWith(@Param("slug") String slug);

    @Transactional
    void deleteBySlug(@Param("slug") String slug);
}
