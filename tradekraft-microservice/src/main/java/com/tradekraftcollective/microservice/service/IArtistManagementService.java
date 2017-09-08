package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by brandonfeist on 9/5/17.
 */
public interface IArtistManagementService {
    Page<Artist> getArtists(int page, int pageSize, String sortField, String sortOrder);

    Artist getArtist(String artistSlug);

    Artist createArtist(Artist artist, MultipartFile imageFile, StopWatch stopWatch);
}
