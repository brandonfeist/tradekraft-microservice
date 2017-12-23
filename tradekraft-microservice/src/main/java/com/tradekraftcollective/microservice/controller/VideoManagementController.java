package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.persistence.entity.Video;
import com.tradekraftcollective.microservice.service.IVideoManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/videos")
public class VideoManagementController {

    @Autowired
    IVideoManagementService videoManagementService;

    @RequestMapping(value = "/randomFeature", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRandomFeatureVideo(
        @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getRandomFeatureVideo, [{}]", xRequestId);

        Video randomVideo = videoManagementService.getRandomFeatureVideo();

        return new ResponseEntity<>(randomVideo, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createVideo(
            @RequestPart("video") Video inputVideo,
            @RequestPart(value = "video_file", required = false) MultipartFile videoFile, // Need a way to relate the array of song files to array of songs for the release
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("createVideo [{}] {}", xRequestId, inputVideo);

        Video video;
        if(videoFile != null) {
            video = videoManagementService.createVideo(inputVideo, videoFile);
        } else {
            video = videoManagementService.createVideo(inputVideo);
        }

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
