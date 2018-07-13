package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.persistence.entity.Video;
import com.tradekraftcollective.microservice.service.IVideoManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/videos")
public class VideoManagementController {
    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "100";
    private static final String SORT_ORDER_DESC = "asc";
    private static final String SORT_FIELD_NAME = "createdAt";

    @Autowired
    IVideoManagementService videoManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getVideos(
        @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM, required = false) Integer page,
        @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortField", defaultValue = SORT_FIELD_NAME, required = false) String sortField,
        @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
        @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getVideos, [{}]", xRequestId);

        Page<Video> videos = videoManagementService.getVideos(page, pageSize, sortField, sortOrder);

        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    @RequestMapping(value = "/randomFeature", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRandomFeatureVideo(
        @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getRandomFeatureVideo, [{}]", xRequestId);

        Video randomVideo = videoManagementService.getRandomFeatureVideo();

        if(randomVideo != null) {
            return new ResponseEntity<>(randomVideo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVideo(
            @RequestBody Video inputVideo,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("createVideo [{}] {}", xRequestId, inputVideo);

        Video video = videoManagementService.createVideo(inputVideo);

        return new ResponseEntity<>(video, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/video", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadVideoFile (
            @RequestPart("video-slug") String videoSlug,
            @RequestPart("video") MultipartFile videoFile,
            @RequestPart("preview-start") String previewStart,
            @RequestPart("preview-end") String previewEnd,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("uploadVideoFile [{}] {}", xRequestId, videoSlug);

        Integer start = Integer.parseInt(previewStart);

        Integer end = Integer.parseInt(previewEnd);

        Video video = videoManagementService.uploadVideoFile(videoSlug, videoFile, start, end);

        return new ResponseEntity<>(video, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteVideo(
            @PathVariable("slug") String videoSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("deleteVideo [{}] {}", xRequestId, videoSlug);

        videoManagementService.deleteVideo(videoSlug);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
