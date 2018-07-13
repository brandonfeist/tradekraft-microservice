package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.media.VideoThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVideoThumbnailRepository extends JpaRepository<VideoThumbnail, Long> {
}
