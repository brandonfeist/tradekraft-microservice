package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by brandonfeist on 10/22/17.
 */
public interface ISongManagementService {
    /**
     * Gets a page of songs depending on given sort and sort order.
     *
     * @param page Song page
     * @param pageSize The size of the song page
     * @param sortField The song field to sort by
     * @param sortOrder The order to sort by the given song field
     * @return Page of songs
     */
    Page<Song> getSongs (int page, int pageSize, String sortField, String sortOrder);

    /**
     * Creates a song and relates it to given release based off the release slug.
     *
     * @param releaseSlug Release slug of release that owns the created song
     * @param song The song to be created
     * @return The created song
     */
    Song createSong(String releaseSlug, Song song);

    /**
     * Deletes the song with given song slug.
     *
     * @param songSlug Slug of song to be deleted
     */
    void deleteSong(String songSlug);

    /**
     * Updates the song with the given song slug using the songUpdates object.
     *
     * @param songUpdates Updates that will be made to the song
     * @param songSlug Slug identifying the song to be updated
     * @return The updated song
     */
    Song updateSong(final Song songUpdates, final String songSlug);

    /**
     * Deletes a given song.
     *
     * @param song The song to be deleted
     */
    void deleteSong(Song song);

    /**
     * Authorizes if user is authenticated and if authenticated user has the correct permissions to view the song.
     * If the user does not have permission, 30 second Spotify mp3s will replace the original song file links.
     *
     * @param release The release to be authorized
     * @return List of songs with song links processed
     */
    List<Song> songLinkAuthorization(Release release);
}
