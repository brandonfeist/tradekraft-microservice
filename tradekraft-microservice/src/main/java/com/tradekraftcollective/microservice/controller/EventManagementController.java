package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.service.IEventManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * Created by brandonfeist on 9/28/17.
 */
@RestController
@RequestMapping("/v1/events")
public class EventManagementController {
    private static final Logger logger = LoggerFactory.getLogger(EventManagementController.class);

    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "100";
    private static final String SORT_ORDER_DESC = "asc";
    private static final String SORT_FIELD_NAME = "startDateTime";

    @Inject
    IEventManagementService eventManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEvents(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM, required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD_NAME, required = false) String sortField,
            @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("getEvents [{}]", xRequestId);

        Page<Event> events = eventManagementService.getEvents(page, pageSize, sortField, sortOrder);

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEvent(
            @PathVariable("slug") String eventSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("getEvent [{}]", xRequestId);

//        Event event = eventManagementService.getArtist(eventSlug);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createEvent(
            @RequestPart("event") Event inputEvent,
            @RequestPart("image") MultipartFile imageFile,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("createEvent [{}] {}", xRequestId, inputEvent);

        StopWatch stopWatch = new StopWatch("createArtist");

        Event event = eventManagementService.createEvent(inputEvent, imageFile, stopWatch);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }
}
