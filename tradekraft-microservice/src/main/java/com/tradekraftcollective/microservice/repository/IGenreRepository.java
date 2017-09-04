package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brandonfeist on 8/31/17.
 */
public interface IGenreRepository extends JpaRepository<Genre, Long> {

}
