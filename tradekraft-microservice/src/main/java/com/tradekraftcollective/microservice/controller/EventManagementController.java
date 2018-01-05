package com.tradekraftcollective.microservice.controller;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Event;
import com.tradekraftcollective.microservice.service.IEventManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
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
            @RequestPart("event-slug") String eventSlug,
            @RequestPart("image") MultipartFile imageFile,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("uploadEventImage [{}] {}", xRequestId, eventSlug);

        Event event = eventManagementService.uploadEventImage(eventSlug, imageFile);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchEvent(
            @PathVariable("slug") String eventSlug,
            @RequestBody List<JsonPatchOperation> patches,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("patchArtist [{}] {}", xRequestId, eventSlug);

        final Event event = eventManagementService.patchEvent(patches, eventSlug);

        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEvent(
            @PathVariable("slug") String eventSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("deleteArtist [{}] {}", xRequestId, eventSlug);

        eventManagementService.deleteEvent(eventSlug);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
