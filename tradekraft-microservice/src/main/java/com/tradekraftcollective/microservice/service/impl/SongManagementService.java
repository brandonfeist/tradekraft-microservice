package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.model.Credentials;
import com.tradekraftcollective.microservice.model.spotify.SpotifyAlbum;
import com.tradekraftcollective.microservice.model.spotify.SpotifySimpleTrack;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.persistence.entity.media.AudioFile;
import com.tradekraftcollective.microservice.persistence.entity.media.SongFile;
import com.tradekraftcollective.microservice.repository.*;
import com.tradekraftcollective.microservice.service.ISongManagementService;
import com.tradekraftcollective.microservice.service.ISpotifyManagementService;
import com.tradekraftcollective.microservice.utilities.SpotifyUrlUtil;
import com.tradekraftcollective.microservice.validator.ArtistValidator;
import com.tradekraftcollective.microservice.validator.GenreValidator;
import com.tradekraftcollective.microservice.validator.ReleaseValidator;
import com.tradekraftcollective.microservice.validator.SongValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Slf4j
@Service
public class SongManagementService implements ISongManagementService {
    private static final String DESCENDING = "desc";

    @Inject
    ISongRepository songRepository;

    @Autowired
    SongValidator songValidator;

    @Autowired
    ReleaseValidator releaseValidator;

    @Autowired
    GenreValidator genreValidator;

    @Autowired
    ArtistValidator artistValidator;

    @Inject
    ISpotifyManagementService spotifyManagementService;

    @Inject
    SpotifyUrlUtil spotifyUrlUtil;

    @Inject
    Credentials credentials;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Song> getSongs(int page, int pageSize, String sortField, String sortOrder) {
        log.info("Fetching songs, page: {} pageSize: {} sortField: {} sortOrder: {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        Page<Song> songPage = songRepository.findAll(request);

        return songPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Song createSong(String releaseSlug, Song song) {
        log.info("Creating song {}", song.getName());

        Release release = releaseValidator.validateReleaseSlug(releaseSlug);

        songValidator.validateSong(song);

        song.setGenre(genreValidator.validateGenreNameExists(song.getGenre().getName()));

        List<Artist> finalArtistList = new ArrayList<>();
        for(Artist artist : song.getArtists()) {
            finalArtistList.add(artistValidator.validateArtistSlug(artist.getSlug()));
        }
        song.setArtists(finalArtistList);

        song.setRelease(release);

        song.setSlug(createSongSlug(song.getName()));

        Song returnSong = songRepository.save(song);

        log.info("***** SUCCESSFULLY CREATED SONG WITH SLUG = {} *****", returnSong.getSlug());

        return returnSong;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSong(Song song) {
        log.info("Delete song, name: {}", song.getName());

        songRepository.delete(song);

        log.info("***** SUCCESSFULLY DELETED SONG WITH SLUG = {} *****", song.getSlug());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSong(String songSlug) {
        log.info("Delete song, slug: {}", songSlug);

        Song song = songRepository.findBySlug(songSlug);

        if(song == null) {
            log.error("Song with slug {} does not exist.", songSlug);
            throw new ServiceException(ErrorCode.INVALID_SONG_SLUG, "Song with slug " + songSlug + " does not exist.");
        }

        songRepository.delete(song);

        log.info("***** SUCCESSFULLY DELETED SONG WITH SLUG = {} *****", song.getSlug());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Song updateSong(final Song songUpdates, final String songSlug) {
        Song song = songRepository.findBySlug(songSlug);
        if(song == null) {
            log.error("Song with slug [{}] does not exist", songSlug);
            throw new ServiceException(ErrorCode.INVALID_SONG_SLUG, "Song with slug [" + songSlug + "] does not exist");
        }

        if(!songUpdates.getName().equals(song.getName())) {
            song.setSlug(createSongSlug(songUpdates.getName()));
        }

        song = songUpdates(song, songUpdates);

        songValidator.validateSong(song);

        songRepository.save(song);

        log.info("***** SUCCESSFULLY UPDATED SONG WITH SLUG = {} *****", song.getSlug());

        return song;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Song> songLinkAuthorization(Release release) {
        List<Song> songs = Optional.ofNullable(release)
                .map(Release::getSongs)
                .orElse(Collections.emptyList());

        if(release != null && (credentials.getAuthorization() == null || !credentials.hasPermission("PREMIUM_MUSIC_PERMISSION"))
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

    private Song songUpdates(Song originalSong, final Song songUpdates) {
        originalSong.setName(songUpdates.getName());
        originalSong.setSongFile(songUpdates.getSongFile());
        originalSong.setTrackNumber(songUpdates.getTrackNumber());
        originalSong.setBpm(songUpdates.getBpm());
        originalSong.setRelease(songUpdates.getRelease());
        originalSong.setVideos(songUpdates.getVideos());
        originalSong.setArtists(songUpdates.getArtists());
        originalSong.setGenre(songUpdates.getGenre());

        return originalSong;
    }

    /**
     * Removes and set song file links to null.
     *
     * @param songs Songs to have their song file links removed
     * @return List of songs with song file links removed
     */
    private List<Song> removeSongFileLinks(List<Song> songs) {
        List<Song> editedSongs = new ArrayList<>();
        for (Song song : songs) {
            song.setSongFile(null);
            editedSongs.add(song);
        }

        return editedSongs;
    }

    /**
     * Changes the given song's file links to Spotify 30 seconds preview mp3's.
     *
     * @param songs Songs to be processed with Spotify mp3 links
     * @param spotifyTracks The Spotify tracks that relate to given songs
     * @return List of songs with their song file links replaced with Spotify mp3 links
     */
    private List<Song> changeSongFileToExternalSpotifyLinks(List<Song> songs, List<SpotifySimpleTrack> spotifyTracks) {
        List<Song> editedSongs = new ArrayList<>();

        for(int songIndex = 0; songIndex < songs.size(); songIndex++) {

            Song songToEdit = songs.get(songIndex);

            AudioFile audioFile = songToEdit.getSongFile();
                    new SongFile("external", spotifyTracks.get(songIndex).getPreviewUrl());

            audioFile.setLink(spotifyTracks.get(songIndex).getPreviewUrl());

            songToEdit.setSongFile(audioFile);

            editedSongs.add(songToEdit);
        }

        return editedSongs;
    }

    /**
     * Creates a unique song slug.
     *
     * @param songName The name of the song
     * @return A unique song slug
     */
    private String createSongSlug(String songName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(songName);

        List<Song> duplicateSongs = songRepository.findBySlugStartingWith(result);

        for(Song song : duplicateSongs) {
            if(song.getSlug().equals(result)) {
                return result.concat("-" + (duplicateSongs.size() + 1));
            }
        }

        return result;
    }
}
