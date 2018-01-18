package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.media.SongFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISongFileRepository extends JpaRepository<SongFile, Long> {
}
