package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.media.VideoFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVideoFileRepository extends JpaRepository<VideoFile, Long> {
}
