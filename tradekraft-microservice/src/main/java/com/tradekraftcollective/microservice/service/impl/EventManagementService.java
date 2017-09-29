package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.repository.IEventRepository;
import com.tradekraftcollective.microservice.service.IEventManagementService;
import com.tradekraftcollective.microservice.utilities.ImageProcessingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

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

    @Inject
    ImageProcessingUtil imageProcessingUtil;

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

    // Get event

    @Override
    public Event createEvent(Event event, MultipartFile imageFile, StopWatch stopWatch) {
        logger.info("Create event, name: {}", event.getName());

        stopWatch.start("validateEvent");
//        artistValidator.validateArtist(artist, imageFile);
        stopWatch.stop();

        stopWatch.start("saveEvent");

        event.setSlug(createEventSlug(event.getName()));

        event.setImage(imageProcessingUtil.processImageAndUpload(event.getImageSizes(),
                (event.EVENT_IMAGE_UPLOAD_PATH + event.getSlug() + "/"),
                imageFile, 1.0));

        Event returnEvent = eventRepository.save(event);

        stopWatch.stop();
        logger.info("***** SUCCESSFULLY CREATED EVENT WITH SLUG = {} *****", returnEvent.getSlug());

        return returnEvent;
    }

    private String createEventSlug(String eventName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(eventName);

        int duplicateSlugs = eventRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }
}
