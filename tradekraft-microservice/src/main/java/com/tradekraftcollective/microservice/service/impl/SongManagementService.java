package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import com.tradekraftcollective.microservice.repository.ISongRepository;
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

    @Inject
    ISongRepository songRepository;

    @Inject
    IGenreRepository genreRepository;

    @Inject
    IArtistRepository artistRepository;

    @Inject
    AudioProcessingUtil audioProcessingUtil;

    public Song createSong(Song song, MultipartFile songFile) {
        logger.info("Processing song {}", song.getName());

        song.setGenre(genreRepository.findByName(song.getGenre().getName()));

        Set<Artist> finalArtistSet = new HashSet<>();
        for(Artist artist : song.getArtists()) {
            finalArtistSet.add(artistRepository.findBySlug(artist.getSlug()));
        }
        song.setArtists(finalArtistSet);

        song.setSlug(createSongSlug(song.getName()));

        song.setSongFile(audioProcessingUtil.processAudioAndUpload(song.getAudioFormats(), song,
                (song.SONG_AUDIO_UPLOAD_PATH + song.getSlug() + "/"), songFile));

        return song;
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
