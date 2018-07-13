package com.tradekraftcollective.microservice.controller;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.constants.ImageFileTypeConstants;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import com.tradekraftcollective.microservice.service.IEventManagementService;
import com.tradekraftcollective.microservice.service.IImageManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Slf4j
@RestController
@RequestMapping("/v1/events")
public class EventManagementController {
    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "4";
    private static final String SORT_ORDER_DESC = "asc";
    private static final String SORT_FIELD_NAME = "startDateTime";

    private static final int MIN_IMAGE_HEIGHT = -1;
    private static final int MIN_IMAGE_WIDTH = 1024;
    private static final String[] WHITELISTED_IMAGE_FILE_TYPES = {
            ImageFileTypeConstants.JPG
    };

    @Autowired
    private IImageManagementService imageManagementService;

    @Inject
    IEventManagementService eventManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEvents(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM, required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD_NAME, required = false) String sortField,
            @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
            @RequestParam(value = "officialEvents", defaultValue = "false", required = false) boolean officialEventsOnly,
            @RequestParam(value = "pastEvents", defaultValue = "false", required = false) boolean pastEvents,
            @RequestParam(value = "futureEvents", defaultValue = "false", required = false) boolean futureEvents,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getEvents [{}]", xRequestId);

        Page<Event> events = eventManagementService.getEvents(page, pageSize, sortField, sortOrder, officialEventsOnly, pastEvents, futureEvents);

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEvent(
            @PathVariable("slug") String eventSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getEvent [{}]", xRequestId);

        Event event = eventManagementService.getEvent(eventSlug);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createEvent(
            @RequestBody Event inputEvent,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("createEvent [{}] {}", xRequestId, inputEvent);

        Event event = eventManagementService.createEvent(inputEvent);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadEventImage (
            @RequestPart("image") MultipartFile imageFile,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("uploadEventImage [{}] {}", imageFile.getOriginalFilename(), xRequestId);

        Image image = imageManagementService.uploadImage(imageFile, MIN_IMAGE_HEIGHT, MIN_IMAGE_WIDTH, WHITELISTED_IMAGE_FILE_TYPES);

        return new ResponseEntity<>(image, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEvent(
            @PathVariable("slug") String eventSlug,
            @RequestBody final Event eventUpdates,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("updateEvent [{}] {}", xRequestId, eventSlug);

        final Event event = eventManagementService.updateEvent(eventUpdates, eventSlug);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEvent(
            @PathVariable("slug") String eventSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("deleteEvent [{}] {}", xRequestId, eventSlug);

        eventManagementService.deleteEvent(eventSlug);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
