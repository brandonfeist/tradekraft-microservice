package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by brandonfeist on 9/28/17.
 */
public interface IEventRepository extends JpaRepository<Event, Long> {
    List<Event> findBySlugStartingWith(@Param("slug") String slug);
}
