package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.media.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAudioFileRepository extends JpaRepository<AudioFile, Long> {
}
