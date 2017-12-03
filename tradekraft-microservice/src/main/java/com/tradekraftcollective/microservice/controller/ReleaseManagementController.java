package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.service.IReleaseManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

/**
 * Created by brandonfeist on 10/21/17.
 */
@Slf4j
@RestController
@RequestMapping("/v1/releases")
public class ReleaseManagementController {
    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "100";
    private static final String SORT_ORDER_DESC = "asc";
    private static final String SORT_FIELD_NAME = "releaseDate";

    @Inject
    private IReleaseManagementService releaseManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReleases(
            @RequestParam(value = "search", required = false) String searchQuery,
            @RequestParam(value = "genre", required = false) String genreQuery,
            @RequestParam(value = "type", required = false) String typeQuery,
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM, required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD_NAME, required = false) String sortField,
            @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getReleases [{}], searchQuery [{}], genreQuery [{}], typeQuery [{}]", xRequestId, searchQuery, genreQuery, typeQuery);

        Page<Release> releases = releaseManagementService.getReleases(page, pageSize, sortField, sortOrder,
                searchQuery, genreQuery, typeQuery);

        return new ResponseEntity<>(releases, HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRelease(
            @PathVariable("slug") String releaseSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getRelease [{}]", xRequestId);

        Release release = releaseManagementService.getRelease(releaseSlug);

        return new ResponseEntity<>(release, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRelease(
            @RequestPart("release") Release inputRelease,
            @RequestPart("image") MultipartFile imageFile,
            @RequestPart("song_files") MultipartFile[] songFiles, // Need a way to relate the array of song files to array of songs for the release
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("createRelease [{}] {}", xRequestId, inputRelease);

        Release release = releaseManagementService.createRelease(inputRelease, imageFile, songFiles);

        return new ResponseEntity<>(release, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRelease(
            @PathVariable("slug") String releaseSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("deleteRelease [{}] {}", xRequestId, releaseSlug);

        releaseManagementService.deleteRelease(releaseSlug);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
