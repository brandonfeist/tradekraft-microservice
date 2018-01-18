package com.tradekraftcollective.microservice.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.model.Credentials;
import com.tradekraftcollective.microservice.model.spotify.SpotifyAlbum;
import com.tradekraftcollective.microservice.model.spotify.SpotifySimpleTrack;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.persistence.entity.media.SongFile;
import com.tradekraftcollective.microservice.repository.*;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.ISongManagementService;
import com.tradekraftcollective.microservice.service.ISpotifyManagementService;
import com.tradekraftcollective.microservice.utilities.AudioProcessingUtil;
import com.tradekraftcollective.microservice.utilities.SpotifyUrlUtil;
import com.tradekraftcollective.microservice.validator.SongValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Slf4j
@Service
public class SongManagementService implements ISongManagementService {
    private static final String SONG_FILE_PATH = "uploads/song/release-song/";

    @Inject
    ISongRepository songRepository;

    @Autowired
    IReleaseRepository releaseRepository;

    @Inject
    IGenreRepository genreRepository;

    @Inject
    IArtistRepository artistRepository;

    @Autowired
    ISongFileRepository songFileRepository;

    @Autowired
    SongValidator songValidator;

    @Inject
    ISpotifyManagementService spotifyManagementService;

    @Inject
    SpotifyUrlUtil spotifyUrlUtil;

    @Inject
    AudioProcessingUtil audioProcessingUtil;

    @Inject
    AmazonS3Service amazonS3Service;

    @Inject
    Credentials credentials;

    @Override
    public Song createSong(String releaseSlug, Song song) {
        log.info("Processing song {}", song.getName());

        Release release = releaseRepository.findBySlug(releaseSlug);

        if(release == null) {
            log.error("Release with slug [{}] does not exist", releaseSlug);
            throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "Release with slug [" + releaseSlug + "] does not exist");
        }

        songValidator.validateSong(song);

        song.setGenre(genreRepository.findByName(song.getGenre().getName()));

        List<Artist> finalArtistList = new ArrayList<>();
        for(Artist artist : song.getArtists()) {
            finalArtistList.add(artistRepository.findBySlug(artist.getSlug()));
        }
        song.setArtists(finalArtistList);

        song.setRelease(release);

        song.setSlug(createSongSlug(song.getName()));

        return song;
    }

    @Override
    public Song uploadSongFile(String songSlug, MultipartFile songFile) {
        log.info("Uploading song file for song slug [{}]", songSlug);

        Song returnSong = songRepository.findBySlug(songSlug);

        if(returnSong == null) {
            log.error("Song with slug [{}] does not exist", songSlug);
            throw new ServiceException(ErrorCode.INVALID_SONG_SLUG, "Song with slug [" + songSlug + "] does not exist");
        }

        songValidator.validateSongFile(songFile);

        deleteAllSongFiles(returnSong);

        HashMap<String, SongFile> songFileHash = audioProcessingUtil.processAudioHashAndUpload(returnSong.getAudioFormats(),
                returnSong.getRelease(), returnSong, returnSong.getAWSKey(), returnSong.getAWSUrl(), songFile, SongFile.class);

        saveSongFilesToRepo(songFileHash, returnSong);

        returnSong.setSongFiles(songFileHash);

        returnSong = songRepository.save(returnSong);

        log.info("***** SUCCESSFULLY UPLOADED SONG FILE FOR SONG = {} *****", returnSong.getSlug());

        return returnSong;
    }

    private void saveSongFilesToRepo(HashMap<String, SongFile> map, Song song) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            SongFile songFile = (SongFile) pair.getValue();
            songFile.setSong(song);

            songFileRepository.save((SongFile) pair.getValue());

            it.remove();
        }
    }

    @Override
    public void deleteSong(Song song) {
        log.info("Delete song, name: {}", song.getName());

        deleteAllSongFiles(song);

        songRepository.delete(song);

        log.info("***** SUCCESSFULLY DELETED SONG WITH SLUG = {} *****", song.getSlug());
    }

    @Override
    public void deleteSong(String songSlug) {
        log.info("Delete song, slug: {}", songSlug);

        Song song = songRepository.findBySlug(songSlug);

        if(song == null) {
            log.error("Song with slug {} does not exist.", songSlug);
            throw new ServiceException(ErrorCode.INVALID_SONG_SLUG, "Song with slug " + songSlug + " does not exist.");
        }

        deleteAllSongFiles(song);

        songRepository.delete(song);

        log.info("***** SUCCESSFULLY DELETED SONG WITH SLUG = {} *****", song.getSlug());
    }

    @Override
    public HashMap<String, MultipartFile> createSongFileHashMap(MultipartFile[] songFiles) {
        HashMap<String, MultipartFile> songFileHashMap = new HashMap<>();
        for(MultipartFile songFile : songFiles) {
            songFileHashMap.put(songFile.getOriginalFilename(), songFile);
        }

        return songFileHashMap;
    }

    @Override
    public List<Song> songLinkAuthorization(Release release) {
        List<Song> songs = release.getSongs();

        if((credentials.getAuthorization() == null || !credentials.hasPermission("PREMIUM_MUSIC_PERMISSION"))
                && !release.isFreeRelease()) {
            log.info("User does not have access to premium songs...");

            List<Song> editedSongList;

            editedSongList = removeSongFileLinks(release.getSongs());

            SpotifyAlbum spotifyAlbum = null;
            if(release.getSpotify() != null) {
                spotifyAlbum = spotifyManagementService.getSpotifyAlbumInformation(spotifyUrlUtil.getAlbumId(release.getSpotify()));
            }

            if(release.getSpotify() != null && spotifyAlbum != null) {
                log.info("Retrieving Spotify song preview mp3 links.");

                List<SpotifySimpleTrack> spotifyTracks = spotifyAlbum.getTracks().getItems();

                for(int songIndex = 0; songIndex < songs.size(); songIndex++) {
                    editedSongList = changeSongFileToExternalSpotifyLinks(release.getSongs(), spotifyTracks);
                }
            }

            return editedSongList;
        }

        // Also make null if song is not released yet?
        // This goes into a whole other issue of gold early release permissions,
        // For example can certain users get permission to hear the song early?

        return songs;
    }

    private List<Song> removeSongFileLinks(List<Song> songs) {
        List<Song> editedSongs = new ArrayList<>();
        for (Song song : songs) {
            song.setSongFiles(null);
            editedSongs.add(song);
        }

        return editedSongs;
    }

    private List<Song> changeSongFileToExternalSpotifyLinks(List<Song> songs, List<SpotifySimpleTrack> spotifyTracks) {
        List<Song> editedSongs = new ArrayList<>();

        for(int songIndex = 0; songIndex < songs.size(); songIndex++) {
            HashMap<String, SongFile> songMap = new HashMap<>();

            Song songToEdit = songs.get(songIndex);

            SongFile songFile = new SongFile("external", spotifyTracks.get(songIndex).getPreviewUrl());
            songMap.put(songFile.getName(), songFile);

            songToEdit.setSongFiles(songMap);

            editedSongs.add(songToEdit);
        }

        return editedSongs;
    }

    private String createSongSlug(String songName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(songName);

        int duplicateSlugs = songRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }

    private void deleteAllSongFiles(Song song) {
        Map<String, SongFile> songFiles = song.getSongFiles();

        ObjectListing directoryImages = amazonS3Service.getDirectoryContent(song.getAWSKey(), null);
        for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
            if(amazonS3Service.doesObjectExist(summary.getKey())) {
                amazonS3Service.delete(summary.getKey());
            }
        }

        for (Map.Entry<String, SongFile> entry : songFiles.entrySet()) {
            songFileRepository.delete(entry.getValue());
        }
    }
}
