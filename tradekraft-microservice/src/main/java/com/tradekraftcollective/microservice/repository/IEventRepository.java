package com.tradekraftcollective.microservice.repository;

import com.tradekraftcollective.microservice.persistence.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by brandonfeist on 9/28/17.
 */
public interface IEventRepository extends JpaRepository<Event, Long> {

}
