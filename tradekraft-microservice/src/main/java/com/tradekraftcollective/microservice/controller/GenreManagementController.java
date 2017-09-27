package com.tradekraftcollective.microservice.controller;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Genre;
import com.tradekraftcollective.microservice.service.IGenreManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by brandonfeist on 9/26/17.
 */
@RestController
@RequestMapping("/v1/genres")
public class GenreManagementController {
    private static final Logger logger = LoggerFactory.getLogger(GenreManagementController.class);

    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "100";
    private static final String SORT_ORDER_DESC = "asc";
    private static final String SORT_FIELD_NAME = "name";

    @Inject
    IGenreManagementService genreManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGenres(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM, required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD_NAME, required = false) String sortField,
            @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("getGenres [{}]", xRequestId);

        Page<Genre> genres = genreManagementService.getGenres(page, pageSize, sortField, sortOrder);

        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getGenre(
            @PathVariable("id") Long genreId,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("getGenre [{}]", xRequestId);

        Genre genre = genreManagementService.getGenre(genreId);

        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createGenre(
            @RequestBody Genre inputGenre,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("createGenre [{}] {}", xRequestId, inputGenre);

        StopWatch stopWatch = new StopWatch("createGenre");

        Genre genre = genreManagementService.createGenre(inputGenre, stopWatch);

        return new ResponseEntity<>(genre, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> patchGenre(
            @PathVariable("id") Long genreId,
            @RequestBody List<JsonPatchOperation> patches,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("patchGenre [{}] {}", xRequestId, genreId);

        StopWatch stopWatch = new StopWatch("patchGenre");

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGenre(
            @PathVariable("id") Long genreId,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        logger.info("deleteGenre [{}] {}", xRequestId, genreId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
