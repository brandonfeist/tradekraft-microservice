package com.tradekraftcollective.microservice.controller;

import com.tradekraftcollective.microservice.constants.AudioFileTypeConstants;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.persistence.entity.media.AudioFile;
import com.tradekraftcollective.microservice.service.IAudioFileManagementService;
import com.tradekraftcollective.microservice.service.ISongManagementService;
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
@RequestMapping("/v1/songs")
public class SongManagementController {
    private static final String DEFAULT_PAGE_NUM = "0";
    private static final String DEFAULT_PAGE_SIZE = "100";
    private static final String SORT_ORDER_DESC = "asc";
    private static final String SORT_FIELD_NAME = "createdAt";
    private static final String[] WHITELISTED_AUDIO_FILE_TYPES = {
            AudioFileTypeConstants.WAV
    };

    @Autowired
    IAudioFileManagementService audioFileManagementService;

    @Autowired
    ISongManagementService songManagementService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSongs(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM, required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = SORT_FIELD_NAME, required = false) String sortField,
            @RequestParam(value = "sortOrder", defaultValue = SORT_ORDER_DESC, required = false) String sortOrder,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("getSongs [{}]", xRequestId);

        Page<Song> songs = songManagementService.getSongs(page, pageSize, sortField, sortOrder);

        return new ResponseEntity<>(songs, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSongs (
            @RequestBody Song[] inputSongs,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("createSong [{}] {}", xRequestId, inputSongs);

        Song[] returnSongs = new Song[inputSongs.length];
        for(int songIndex = 0; songIndex < inputSongs.length; songIndex++) {
            returnSongs [songIndex] = songManagementService.createSong(inputSongs[songIndex].getRelease().getSlug(), inputSongs[songIndex]);
        }

        return new ResponseEntity<>(returnSongs, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/songFile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadSongFile (
            @RequestPart("song") MultipartFile songFile,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("uploadSongFile [{}] {}", xRequestId, songFile.getOriginalFilename());

        AudioFile audioFile = audioFileManagementService.uploadAudioFile(songFile, WHITELISTED_AUDIO_FILE_TYPES);

        return new ResponseEntity<>(audioFile, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSong(
            @PathVariable("slug") String songSlug,
            @RequestBody final Song songUpdates,
            @RequestHeader(value = "X-Request-ID", required = false) String xRequestId
    ) {
        log.info("updateSong [{}] {}", xRequestId, songSlug);

        final Song song = songManagementService.updateSong(songUpdates, songSlug);

        return new ResponseEntity<>(song, HttpStatus.OK);
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
