package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * Created by brandonfeist on 10/22/17.
 */
public interface ISongManagementService {
    Song createSong(Release release, Song song, MultipartFile songFile);

    HashMap<String, MultipartFile> createSongFileHashMap(MultipartFile[] songFiles);

    void deleteSong(Song song);
}
