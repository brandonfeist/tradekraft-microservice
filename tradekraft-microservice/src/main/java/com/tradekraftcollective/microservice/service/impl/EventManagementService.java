package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.repository.IEventRepository;
import com.tradekraftcollective.microservice.service.IEventManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Service
public class EventManagementService implements IEventManagementService {
    private static final Logger logger = LoggerFactory.getLogger(EventManagementService.class);

    private static final String DESCENDING = "desc";
    private static final String EVENT_IMAGE_PATH = "uploads/event/image/";

    @Inject
    IEventRepository eventRepository;

    @Override
    public Page<Event> getEvents(int page, int pageSize, String sortField, String sortOrder) {
        logger.info("Fetching events, page: {} pageSize {} sortField {} sortOrder {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        return eventRepository.findAll(request);
    }
}
