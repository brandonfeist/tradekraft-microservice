package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Slf4j
@Component
public class SongValidator {

    @Inject
    private IGenreRepository genreRepository;

    public void validateReleaseSongs(List<Song> songs, MultipartFile[] songFiles) {

        if(songs.size() != songFiles.length) {
            log.error("Number of songs and song files mismatch");
            throw new ServiceException(ErrorCode.INVALID_SONG_COUNT, "the number of songs: " + songs.size() + " and number of song files: " + songFiles.length + " do not match.");
        }

        HashMap<String, MultipartFile> songFileHashMap = new HashMap<>();
        for(MultipartFile songFile : songFiles) {
            songFileHashMap.put(songFile.getOriginalFilename(), songFile);
        }

        for(Song song : songs) {
            validateSong(song, songFileHashMap.get(song.getSongFileName()));
        }

        validateSongTrackNumbers(songs);
    }

    public void validateSong(Song song, MultipartFile songFile) {
        if(songFile == null) {
            log.error("Song file matching given file name does not exist.");
            throw new ServiceException(ErrorCode.INVALID_SONG_FILE, "song file with matching file name must be present.");
        }

        validateIfGenreExists(song);
        validateIfArtistExists(song);
        validateSongFile(songFile);
    }

    private void validateIfGenreExists(Song song) {
        // Check if genre is not null
        // Check if genre name is not null
        if(genreRepository.findByName(song.getGenre().getName()) == null) {
            log.error("Genre {} does not exist", song.getGenre().getName());
            throw new ServiceException(ErrorCode.INVALID_GENRE, "genre " + song.getGenre().getName() + " does not exist.");
        }
    }

    private void validateIfArtistExists(Song song) {
        // Check if artists is not empty/null
        // Check if each artist slug is not null
        // Check if artist with slug exists
    }

    private void validateSongFile(MultipartFile songFile) {

    }

    private void validateSongTrackNumbers(List<Song> songs) {
        HashMap<Integer, Integer> trackNumberHash = new HashMap<>();

        int songCount = songs.size();
        for(Song song : songs) {
            if(song.getTrackNumber() < 1) {
                log.error("Song track numbers cannot be less than 1.");
                throw new ServiceException(ErrorCode.INVALID_SONG_TRACK_NUMBER, "song track numbers cannot be less than 1.");
            }

            if(song.getTrackNumber() > songCount) {
                log.error("The track number [{}] is larger than the number of songs that exist in the release: [{}]",
                        song.getTrackNumber(), songCount);
                throw new ServiceException(ErrorCode.INVALID_SONG_TRACK_NUMBER,
                        "track number " + song.getTrackNumber() + " is larger than the number of songs that exist in the release " + songCount);
            }

            if(trackNumberHash.get(song.getTrackNumber()) != null) {
                log.error("Two songs with track number [{}] exist in this release.", song.getTrackNumber());
                throw new ServiceException(ErrorCode.INVALID_SONG_TRACK_NUMBER,
                        "more than one song with track number " + song.getTrackNumber() + " exist in this release.");
            }

            trackNumberHash.put(song.getTrackNumber(), song.getTrackNumber());
        }
    }
}
