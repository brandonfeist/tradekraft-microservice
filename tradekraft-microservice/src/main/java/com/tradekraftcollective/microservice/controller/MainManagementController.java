package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by brandonfeist on 8/31/17.
 */
@RestController
@RequestMapping("/v1/")
public class MainManagementController {
    private static final Logger logger = LoggerFactory.getLogger(MainManagementController.class);

    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "100";
    private static final String SORT_ORDER_DESC = "desc";
    private static final String SORT_FIELD_NAME = "name";

    @Inject
    private IArtistRepository artistRepository;

    @RequestMapping(value = "/artists", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> getArtists(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM, required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD_NAME, required = false) String sortField,
            @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("getArtists [{}]", xRequestId);
        List<Artist> artists = artistRepository.findAll();

        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = "/artists/{slug}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> getArtist(
            @PathVariable("slug") String artistSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("getArtists [{}]", xRequestId);

        artistSlug = artistSlug.toLowerCase();
        Artist artist = artistRepository.findBySlug(artistSlug);

        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    // Patch artist

    // Delete artist


    // Get release(s)

    // Get release

    // Patch release

    // Delete release


    // Get event(s)

    // Get event

    // Patch event

    // Delete event


    // Get genre(s)

    // Patch genre

    // Delete genre
}
