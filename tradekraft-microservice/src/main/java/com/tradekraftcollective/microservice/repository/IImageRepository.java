package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image, Long> {
}
