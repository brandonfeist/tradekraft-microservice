package com.tradekraftcollective.microservice.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by brandonfeist on 9/5/17.
 */
public interface IArtistManagementService {

    /**
     * Gets a page of artists and sorts by given artist field and sort order.
     *
     * @param page The page number of artists
     * @param pageSize The number of artist in a single page
     * @param sortField The artist field to sort by
     * @param sortOrder Sort by either asc or desc order
     * @param artistQuery Return artist who's name are similar to this query, null for no query
     * @param yearQuery Return artist the year artist were signed by tradekraft, null for no query
     *
     * @return A page of artists
     */
    Page<Artist> getArtists(int page, int pageSize, String sortField, String sortOrder, String artistQuery, String yearQuery);

    /**
     * Returns a single artist with specified slug.
     *
     * @param artistSlug The artist slug
     *
     * @return Artist who's slug matches artistSlug
     */
    Artist getArtist(String artistSlug);

    /**
     * Create and save an artist in the database.
     *
     * @param artist The artist to be created and saved
     *
     * @return The artist that is created
     */
    Artist createArtist(Artist artist);

    /**
     * Updates the artist that has the given artist slug using the artistUpdates Artist object.
     *
     * @param artistUpdates Updates to be made to the artist
     * @param artistSlug The slug of the artist to be updated
     * @return The updated artist
     */
    Artist updateArtist(final Artist artistUpdates, final String artistSlug);

    /**
     * Delete the artist who's slug matches the artist slug String.
     *
     * @param artistSlug The artist slug
     */
    void deleteArtist(String artistSlug);
}
