package com.tradekraftcollective.microservice.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.constants.PatchOperationConstants;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IEventRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IEventManagementService;
import com.tradekraftcollective.microservice.service.IEventPatchService;
import com.tradekraftcollective.microservice.specification.EventSpecification;
import com.tradekraftcollective.microservice.specification.SearchCriteria;
import com.tradekraftcollective.microservice.utilities.ImageProcessingUtil;
import com.tradekraftcollective.microservice.validator.EventValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Slf4j
@Service
public class EventManagementService implements IEventManagementService {
    private static final String DESCENDING = "desc";
    private static final String EVENT_IMAGE_PATH = "uploads/event/image/";

    @Inject
    IEventRepository eventRepository;

    @Inject
    EventValidator eventValidator;

    @Inject
    IEventPatchService eventPatchService;

    @Inject
    IArtistRepository artistRepository;

    @Inject
    ImageProcessingUtil imageProcessingUtil;

    @Inject
    AmazonS3Service amazonS3Service;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Page<Event> getEvents(int page, int pageSize, String sortField, String sortOrder, boolean officialEventsOnly, boolean pastEvents) {
        log.info("Fetching events, page: {} pageSize: {} sortField: {} sortOrder: {} officialEventsOnly: {} pastEvents: {}", page, pageSize, sortField, sortOrder, officialEventsOnly, pastEvents);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        Specification<Event> result = getEventSpecs(officialEventsOnly, pastEvents);

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        if(result != null) {
            return eventRepository.findAll(result, request);
        }

        return eventRepository.findAll(request);
    }

    @Override
    public Event getEvent(String eventSlug) {
        if(eventSlug == null) {
            log.error("Event slug cannot be null");
            throw new ServiceException(ErrorCode.INVALID_EVENT_SLUG, "event slug cannot be null.");
        }

        eventSlug = eventSlug.toLowerCase();

        Event event = eventRepository.findBySlug(eventSlug);

        return event;
    }

    @Override
    public Event createEvent(Event event, MultipartFile imageFile) {
        log.info("Create event, name: {}", event.getName());

        eventValidator.validateEvent(event, imageFile);

        event.setArtists(findAndSetEventArtists(event));

        event.setSlug(createEventSlug(event.getName()));

        event.setImage(imageProcessingUtil.processImageAndUpload(event.getImageSizes(),
                (event.EVENT_IMAGE_UPLOAD_PATH + event.getSlug() + "/"),
                imageFile, 1.0));

        Event returnEvent = eventRepository.save(event);

        log.info("***** SUCCESSFULLY CREATED EVENT WITH SLUG = {} *****", returnEvent.getSlug());

        return returnEvent;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, ServiceException.class, IOException.class})
    public Event patchEvent(List<JsonPatchOperation> patchOperations, MultipartFile imageFile, String eventSlug) {

        Event oldEvent = eventRepository.findBySlug(eventSlug);
        if(oldEvent == null) {
            log.error("Event with slug [{}] does not exist", eventSlug);
            throw new ServiceException(ErrorCode.INVALID_EVENT_SLUG, "Event with slug [" + eventSlug + "] does not exist");
        }

        boolean uploadingNewImage = (imageFile != null);
        if(uploadingNewImage) {
            JsonPatchOperation imageOperation;

            try {
                if (oldEvent.getImage() != null) {
                    log.info("New image found, creating imagePatch operation: [{}], overwriting image: {}",
                            PatchOperationConstants.REPLACE, oldEvent.getImageName());

                    String jsonImageReplacePatch = "{\"op\": \"" + PatchOperationConstants.REPLACE + "\", " +
                            "\"path\": \"" + EventPatchService.EVENT_IMAGE_PATH + "\", " +
                            "\"value\": \"" + imageFile.getOriginalFilename() + "\"}";

                    imageOperation = objectMapper.readValue(jsonImageReplacePatch, JsonPatchOperation.class);

                    patchOperations.add(imageOperation);
                } else {
                    log.info("New image found, creating imagePatch operation: {}", PatchOperationConstants.ADD);

                    String jsonImageReplacePatch = "{\"op\": \"" + PatchOperationConstants.ADD + "\", " +
                            "\"path\": \"" + EventPatchService.EVENT_IMAGE_PATH + "\", " +
                            "\"value\": \"" + imageFile.getOriginalFilename() + "\"}";

                    imageOperation = objectMapper.readValue(jsonImageReplacePatch, JsonPatchOperation.class);

                    patchOperations.add(imageOperation);
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        Event patchedEvent = eventPatchService.patchEvent(patchOperations, oldEvent);

        if(!oldEvent.getName().equals(patchedEvent.getName())) {
            patchedEvent.setSlug(createEventSlug(patchedEvent.getName()));
        }

        if(uploadingNewImage) {
            eventValidator.validateEvent(patchedEvent, imageFile);

            ObjectListing directoryImages = amazonS3Service.getDirectoryContent((EVENT_IMAGE_PATH + eventSlug + "/"), null);
            for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
                amazonS3Service.delete(summary.getKey());
            }

            patchedEvent.setImage(imageProcessingUtil.processImageAndUpload(patchedEvent.getImageSizes(),
                    (patchedEvent.EVENT_IMAGE_UPLOAD_PATH + patchedEvent.getSlug() + "/"),
                    imageFile, 1.0));
        } else {
            eventValidator.validateEvent(patchedEvent);
        }

        eventRepository.save(patchedEvent);

        log.info("***** SUCCESSFULLY PATCHED EVENT WITH SLUG = {} *****", patchedEvent.getSlug());

        return patchedEvent;
    }

    @Override
    public void deleteEvent(String eventSlug) {
        log.info("Delete event, slug: {}", eventSlug);

        eventValidator.validateEventSlug(eventSlug);

        ObjectListing directoryImages = amazonS3Service.getDirectoryContent((EVENT_IMAGE_PATH + eventSlug + "/"), null);
        for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
            amazonS3Service.delete(summary.getKey());
        }

        eventRepository.deleteBySlug(eventSlug);

        log.info("***** SUCCESSFULLY DELETED EVENT WITH SLUG = {} *****", eventSlug);
    }

    private String createEventSlug(String eventName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(eventName);

        int duplicateSlugs = eventRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }

    private List<Artist> findAndSetEventArtists(Event event) {
        List<Artist> eventArtists = new ArrayList<>();

        for(Artist artist : event.getArtists()) {
            Artist checkedArtist = artistRepository.findBySlug(artist.getSlug());
            if(checkedArtist == null) {
                log.error("Event artist with slug [{}] does not exist.", artist.getSlug());
                throw new ServiceException(ErrorCode.INVALID_ARTIST_SLUG, "artist with slug [" + artist.getSlug() + "] does not exist.");
            }

            eventArtists.add(checkedArtist);
        }

        return eventArtists;
    }

    private Specification<Event> getEventSpecs(boolean officialEventsOnly, boolean pastEvents) {
        Specification<Event> result = null;

        if(officialEventsOnly) {
            log.info("Getting specs for officialEvents [{}]", officialEventsOnly);

            EventSpecification officialEventSpec =
                    new EventSpecification(new SearchCriteria("officialEvent", ":", officialEventsOnly));

            result = Specifications.where(officialEventSpec);
        }

        if(pastEvents) {
            log.info("Getting specs for pastEvents [{}]", pastEvents);

            EventSpecification pastEventSpec =
                    new EventSpecification(new SearchCriteria("startDateTime", "<", new Timestamp(System.currentTimeMillis())));

            result = Specifications.where(result).and(pastEventSpec);
        }

        return result;
    }
}
