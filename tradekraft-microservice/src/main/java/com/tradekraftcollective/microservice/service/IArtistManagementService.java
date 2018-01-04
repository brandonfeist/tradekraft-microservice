package com.tradekraftcollective.microservice.service;

import com.github.fge.jsonpatch.JsonPatchOperation;
import com.tradekraftcollective.microservice.persistence.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by brandonfeist on 9/5/17.
 */
public interface IArtistManagementService {
    Page<Artist> getArtists(int page, int pageSize, String sortField, String sortOrder, String artistQuery, String yearQuery);

    Artist getArtist(String artistSlug);

    Artist createArtist(Artist artist);

    Artist uploadArtistImage(String artistSlug, MultipartFile imageFile);

    Artist patchArtist(final List<JsonPatchOperation> patchOperations, final String artistSlug);

    void deleteArtist(String artistSlug);
}
