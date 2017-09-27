package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by brandonfeist on 8/31/17.
 */
public interface IGenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(@Param("name") String name);

    Genre findByColor(@Param("color") String color);
}
