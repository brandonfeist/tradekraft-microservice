package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Artist;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by brandonfeist on 9/4/17.
 */
public interface IArtistRepository extends JpaRepository<Artist, Long> {
    Artist findBySlug(@Param("slug") String slug);

    List<Artist> findBySlugStartingWith(@Param("slug") String slug);

    @Transactional
    void deleteBySlug(@Param("slug") String slug);
}
