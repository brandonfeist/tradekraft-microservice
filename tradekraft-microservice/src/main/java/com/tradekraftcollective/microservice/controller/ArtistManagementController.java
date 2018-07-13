package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.constants.ImageFileTypeConstants;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import com.tradekraftcollective.microservice.service.IArtistManagementService;
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

/**
 * Created by brandonfeist on 9/12/17.
 */
@Slf4j
@RestController
@RequestMapping("/v1/artists")
public class ArtistManagementController {
    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "4";
    private static final String SORT_ORDER_DESC = "asc";
    private static final String SORT_FIELD_NAME = "name";

    private static final int MIN_IMAGE_HEIGHT = 1024;
    private static final int MIN_IMAGE_WIDTH = 1024;
    private static final String[] WHITELISTED_IMAGE_FILE_TYPES = {
            ImageFileTypeConstants.JPG
    };

    @Autowired
    private IImageManagementService imageManagementService;

    @Inject
    private IArtistManagementService artistManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getArtists(
            @RequestParam(value = "artistQuery", required = false) String artistQuery,
            @RequestParam(value = "yearQuery", required = false) String yearQuery,
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM, required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD_NAME, required = false) String sortField,
            @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getArtists [{}], searchQuery [{}], yearQuery [{}]", xRequestId, artistQuery, yearQuery);

        Page<Artist> artists = artistManagementService.getArtists(page, pageSize, sortField, sortOrder, artistQuery, yearQuery);

        return new ResponseEntity<>(artists, HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getArtist(
            @PathVariable("slug") String artistSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getArtist [{}]", xRequestId);

        Artist artist = artistManagementService.getArtist(artistSlug);

        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createArtist (
            @RequestBody Artist inputArtist,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("createArtist [{}] {}", xRequestId, inputArtist);

        Artist artist = artistManagementService.createArtist(inputArtist);

        return new ResponseEntity<>(artist, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadArtistImage (
            @RequestPart("image") MultipartFile imageFile,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("uploadArtistImage [{}] {}", imageFile.getOriginalFilename(), xRequestId);

        Image image = imageManagementService.uploadImage(imageFile, MIN_IMAGE_HEIGHT, MIN_IMAGE_WIDTH, WHITELISTED_IMAGE_FILE_TYPES);

        return new ResponseEntity<>(image, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateArtist(
            @PathVariable("slug") String artistSlug,
            @RequestBody Artist artistUpdates,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("updateArtist [{}] {}", xRequestId, artistSlug);

        final Artist artist = artistManagementService.updateArtist(artistUpdates, artistSlug);

        return new ResponseEntity<>(artist, HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArtist(
            @PathVariable("slug") String artistSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("deleteArtist [{}] {}", xRequestId, artistSlug);

        artistManagementService.deleteArtist(artistSlug);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
