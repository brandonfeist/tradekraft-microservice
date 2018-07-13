package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Release;
import org.springframework.data.domain.Page;

/**
 * Created by brandonfeist on 10/22/17.
 */
public interface IReleaseManagementService {

    Page<Release> getReleases (int page, int pageSize, String sortField, String sortOrder, String searchQuery, String genreQuery, String typeQuery);

    Release getRelease(String releaseSlug);

    Release createRelease(Release release);

    Release updateRelease(final Release releaseUpdates, final String releaseSlug);

    void deleteRelease(String releaseSlug);
}
