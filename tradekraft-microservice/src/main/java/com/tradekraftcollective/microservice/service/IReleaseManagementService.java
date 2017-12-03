package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Release;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by brandonfeist on 10/22/17.
 */
public interface IReleaseManagementService {

    Page<Release> getReleases (int page, int pageSize, String sortField, String sortOrder, String searchQuery, String genreQuery, String typeQuery);

    Release getRelease(String releaseSlug);

    Release createRelease(Release release, MultipartFile imageFile, MultipartFile[] songFiles);

    void deleteRelease(String releaseSlug);
}
