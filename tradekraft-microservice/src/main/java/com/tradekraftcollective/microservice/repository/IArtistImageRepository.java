package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.media.ArtistImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArtistImageRepository extends JpaRepository<ArtistImage, Long> {
}
