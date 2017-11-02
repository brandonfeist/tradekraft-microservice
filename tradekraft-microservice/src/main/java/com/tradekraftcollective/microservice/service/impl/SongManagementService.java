package com.tradekraftcollective.microservice.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import com.tradekraftcollective.microservice.repository.ISongRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.ISongManagementService;
import com.tradekraftcollective.microservice.utilities.AudioProcessingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Service
public class SongManagementService implements ISongManagementService {
    private static Logger logger = LoggerFactory.getLogger(SongManagementService.class);

    private static final String SONG_FILE_PATH = "uploads/song/release-song/";

    @Inject
    ISongRepository songRepository;

    @Inject
    IGenreRepository genreRepository;

    @Inject
    IArtistRepository artistRepository;

    @Inject
    AudioProcessingUtil audioProcessingUtil;

    @Inject
    AmazonS3Service amazonS3Service;

    @Override
    public Song createSong(Release release, Song song, MultipartFile songFile) {
        logger.info("Processing song {}", song.getName());

        song.setGenre(genreRepository.findByName(song.getGenre().getName()));

        List<Artist> finalArtistList = new ArrayList<>();
        for(Artist artist : song.getArtists()) {
            finalArtistList.add(artistRepository.findBySlug(artist.getSlug()));
        }
        song.setArtists(finalArtistList);

        song.setRelease(release);

        song.setSlug(createSongSlug(song.getName()));

        song.setSongFile(audioProcessingUtil.processAudioAndUpload(song.getAudioFormats(), release, song,
                (song.SONG_AUDIO_UPLOAD_PATH + song.getSlug() + "/"), songFile));

        return song;
    }

    @Override
    public void deleteSong(Song song) {
        logger.info("Delete song, name: {}", song.getName());

        ObjectListing directorySongs = amazonS3Service.getDirectoryContent((SONG_FILE_PATH + song.getSlug() + "/"), null);
        for (S3ObjectSummary summary: directorySongs.getObjectSummaries()) {
            amazonS3Service.delete(summary.getKey());
        }
    }

    @Override
    public HashMap<String, MultipartFile> createSongFileHashMap(MultipartFile[] songFiles) {
        HashMap<String, MultipartFile> songFileHashMap = new HashMap<>();
        for(MultipartFile songFile : songFiles) {
            songFileHashMap.put(songFile.getOriginalFilename(), songFile);
        }

        return songFileHashMap;
    }

    private String createSongSlug(String songName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(songName);

        int duplicateSlugs = songRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }
}
