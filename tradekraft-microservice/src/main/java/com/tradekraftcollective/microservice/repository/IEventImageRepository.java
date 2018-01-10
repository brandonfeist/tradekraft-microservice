package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.media.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventImageRepository extends JpaRepository<EventImage, Long> {
}
