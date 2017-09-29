package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by brandonfeist on 9/28/17.
 */
public interface IEventManagementService {
    Page<Event> getEvents(int page, int pageSize, String sortField, String sortOrder);

    Event createEvent(Event event, MultipartFile imageFile, StopWatch stopWatch);
}
