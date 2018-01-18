package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.media.ReleaseImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReleaseImageRepository extends JpaRepository<ReleaseImage, Long> {
}
