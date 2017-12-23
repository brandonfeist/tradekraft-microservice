package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface IVideoRepository extends JpaRepository<Video, Long> {

    Video findBySlug(@Param("slug") String slug);

    List<Video> findBySlugStartingWith(@Param("slug") String slug);

    @Query(value="SELECT * FROM Videos WHERE featured ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Video findRandomFeatureVideo();

    @Transactional
    void deleteBySlug(@Param("slug") String slug);
}
