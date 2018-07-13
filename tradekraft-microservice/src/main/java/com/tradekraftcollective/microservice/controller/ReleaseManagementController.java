package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.constants.ImageFileTypeConstants;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import com.tradekraftcollective.microservice.service.IImageManagementService;
import com.tradekraftcollective.microservice.service.IReleaseManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String DEFAULT_PAGE_SIZE = "26";
    private static final String SORT_ORDER_DESC = "desc";
    private static final String SORT_FIELD_NAME = "releaseDate";

    private static final int MIN_IMAGE_HEIGHT = 1024;
    private static final int MIN_IMAGE_WIDTH = 1024;
    private static final String[] WHITELISTED_IMAGE_FILE_TYPES = {
            ImageFileTypeConstants.JPG
    };

    @Autowired
    private IImageManagementService iImageManagementService;

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

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRelease(
            @RequestBody Release inputRelease,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("createRelease [{}] {}", xRequestId, inputRelease);

        Release release = releaseManagementService.createRelease(inputRelease);

        return new ResponseEntity<>(release, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadReleaseImage (
            @RequestPart("image") MultipartFile imageFile,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("uploadReleaseImage [{}] {}", xRequestId, imageFile.getOriginalFilename());

        Image releaseImage = iImageManagementService.uploadImage(imageFile, MIN_IMAGE_HEIGHT, MIN_IMAGE_WIDTH, WHITELISTED_IMAGE_FILE_TYPES);

        return new ResponseEntity<>(releaseImage, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateRelease(
            @PathVariable("slug") String releaseSlug,
            @RequestBody final Release releaseUpdates,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("updateRelease [{}] {}", xRequestId, releaseSlug);

        final Release release = releaseManagementService.updateRelease(releaseUpdates, releaseSlug);

        return new ResponseEntity<>(release, HttpStatus.OK);
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
