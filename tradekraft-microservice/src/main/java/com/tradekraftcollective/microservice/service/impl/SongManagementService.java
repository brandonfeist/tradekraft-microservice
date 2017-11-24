package com.tradekraftcollective.microservice.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.model.Credentials;
import com.tradekraftcollective.microservice.model.spotify.SpotifyAlbum;
import com.tradekraftcollective.microservice.model.spotify.SpotifySimpleTrack;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.repository.IArtistRepository;
import com.tradekraftcollective.microservice.repository.IGenreRepository;
import com.tradekraftcollective.microservice.repository.ISongRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.ISongManagementService;
import com.tradekraftcollective.microservice.service.ISpotifyManagementService;
import com.tradekraftcollective.microservice.utilities.AudioProcessingUtil;
import com.tradekraftcollective.microservice.utilities.SpotifyUrlUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Inject
    IGenreRepository genreRepository;

    @Inject
    IArtistRepository artistRepository;

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
    public Song createSong(Release release, Song song, MultipartFile songFile) {
        log.info("Processing song {}", song.getName());

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
        log.info("Delete song, name: {}", song.getName());

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
            song.setSongFile(null);
            editedSongs.add(song);
        }

        return editedSongs;
    }

    private List<Song> changeSongFileToExternalSpotifyLinks(List<Song> songs, List<SpotifySimpleTrack> spotifyTracks) {
        List<Song> editedSongs = new ArrayList<>();

        for(int songIndex = 0; songIndex < songs.size(); songIndex++) {
            Song songToEdit = songs.get(songIndex);

            songToEdit.setSongFile(spotifyTracks.get(songIndex).getPreviewUrl());

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
}
