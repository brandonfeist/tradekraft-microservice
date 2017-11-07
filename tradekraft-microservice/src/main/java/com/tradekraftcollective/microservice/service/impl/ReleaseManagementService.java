package com.tradekraftcollective.microservice.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.repository.IReleaseRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IReleaseManagementService;
import com.tradekraftcollective.microservice.service.ISongManagementService;
import com.tradekraftcollective.microservice.utilities.ImageProcessingUtil;
import com.tradekraftcollective.microservice.validator.ReleaseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Slf4j
@Service
public class ReleaseManagementService implements IReleaseManagementService {
    private static final String DESCENDING = "desc";
    private static final String RELEASE_IMAGE_PATH = "uploads/release/image/";

    @Inject
    private IReleaseRepository releaseRepository;

    @Inject
    private ISongManagementService songManagementService;

    @Inject
    private ReleaseValidator releaseValidator;

    @Inject
    ImageProcessingUtil imageProcessingUtil;

    @Inject
    AmazonS3Service amazonS3Service;

    @Override
    public Page<Release> getReleases(int page, int pageSize, String sortField, String sortOrder) {
        log.info("Fetching releases, page: {} pageSize: {} sortField: {} sortOrder: {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        return releaseRepository.findAll(request);
    }

    @Override
    public Release getRelease(String releaseSlug) {
        if(releaseSlug == null) {
            log.error("Release slug cannot be null");
            throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "release slug cannot be null.");
        }

        releaseSlug = releaseSlug.toLowerCase();

        Release release = releaseRepository.findBySlug(releaseSlug);

        return release;
    }

    @Override
    public Release createRelease(Release release, MultipartFile imageFile, MultipartFile[] songFiles) {
        log.info("Create release, name: {}, with {} songs", release.getName(), release.getSongs().size());

        releaseValidator.validateRelease(release, imageFile, songFiles);

        release.setSlug(createReleaseSlug(release.getName()));

        release.setImage(imageProcessingUtil.processImageAndUpload(release.getImageSizes(),
                (release.RELEASE_IMAGE_UPLOAD_PATH + release.getSlug() + "/"),
                imageFile, 1.0));

        List<Song> finalSongSet = new ArrayList<>();
        HashMap<String, MultipartFile> songFileHashMap = songManagementService.createSongFileHashMap(songFiles);
        for(Song song : release.getSongs()) {
            finalSongSet.add(songManagementService.createSong(release, song, songFileHashMap.get(song.getSongFileName())));
        }

        release.setSongs(finalSongSet);

        Release returnRelease = releaseRepository.save(release);

        log.info("***** SUCCESSFULLY CREATED RELEASE WITH SLUG = {} AND NUMBER OF SONG = {} *****", returnRelease.getSlug(), returnRelease.getSongs().size());

        return returnRelease;
    }

    @Override
    public void deleteRelease(String releaseSlug) {
        log.info("Delete release, slug: {}", releaseSlug);

        Release release = releaseRepository.findBySlug(releaseSlug);
        if(release == null) {
            log.error("Release with slug [{}] does not exist", releaseSlug);
            throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "Release with slug [" + releaseSlug + "] does not exist");
        }

        ObjectListing directoryImages = amazonS3Service.getDirectoryContent((RELEASE_IMAGE_PATH + releaseSlug + "/"), null);
        for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
            amazonS3Service.delete(summary.getKey());
        }

        for(Song song : release.getSongs()) {
            songManagementService.deleteSong(song);
        }

        releaseRepository.deleteBySlug(releaseSlug);

        log.info("***** SUCCESSFULLY DELETED RELEASE WITH SLUG = {} *****", releaseSlug);
    }

    private String createReleaseSlug(String releaseName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(releaseName);

        int duplicateSlugs = releaseRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }
}
