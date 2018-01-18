package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.service.ISongManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/v1/songs")
public class SongManagementController {

    @Autowired
    ISongManagementService songManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSong (
            @RequestBody Song inputSong,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("createSong [{}] {}", xRequestId, inputSong);

        Song song = songManagementService.createSong(inputSong.getRelease().getSlug(), inputSong);

        return new ResponseEntity<>(song, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadSongFile (
            @RequestPart("song-slug") String songSlug,
            @RequestPart("song") MultipartFile songFile,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("uploadSongFile [{}] {}", xRequestId, songSlug);

        Song song = songManagementService.uploadSongFile(songSlug, songFile);

        return new ResponseEntity<>(song, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSong(
            @PathVariable("slug") String songSlug,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("deleteSong [{}] {}", xRequestId, songSlug);

        songManagementService.deleteSong(songSlug);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
