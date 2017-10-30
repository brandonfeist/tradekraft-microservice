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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by brandonfeist on 10/22/17.
 */
@Service
public class ReleaseManagementService implements IReleaseManagementService {
    private static Logger logger = LoggerFactory.getLogger(ReleaseManagementService.class);

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
        logger.info("Fetching releases, page: {} pageSize: {} sortField: {} sortOrder: {}", page, pageSize, sortField, sortOrder);

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
            logger.error("Release slug cannot be null");
            throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "release slug cannot be null.");
        }

        releaseSlug = releaseSlug.toLowerCase();

        Release release = releaseRepository.findBySlug(releaseSlug);

        return release;
    }

    @Override
    public Release createRelease(Release release, MultipartFile imageFile, MultipartFile[] songFiles, StopWatch stopWatch) {
        logger.info("Create release, name: {}, with {} songs", release.getName(), release.getSongs().size());

        stopWatch.start("validateReleaseAndSongs");
        releaseValidator.validateRelease(release, imageFile, songFiles);
        stopWatch.stop();

        stopWatch.start("saveReleasesAndSongs");

        release.setSlug(createReleaseSlug(release.getName()));

        release.setImage(imageProcessingUtil.processImageAndUpload(release.getImageSizes(),
                (release.RELEASE_IMAGE_UPLOAD_PATH + release.getSlug() + "/"),
                imageFile, 1.0));

        Set<Song> finalSongSet = new HashSet<>();
        HashMap<String, MultipartFile> songFileHashMap = songManagementService.createSongFileHashMap(songFiles);
        for(Song song : release.getSongs()) {
            finalSongSet.add(songManagementService.createSong(release, song, songFileHashMap.get(song.getSongFile())));
        }

        release.setSongs(finalSongSet);

        Release returnRelease = releaseRepository.save(release);

        stopWatch.stop();

        logger.info("***** SUCCESSFULLY CREATED RELEASE WITH SLUG = {} AND NUMBER OF SONG = {} *****", returnRelease.getSlug(), returnRelease.getSongs().size());

        return returnRelease;
    }

    @Override
    public void deleteRelease(String releaseSlug) {
        logger.info("Delete release, slug: {}", releaseSlug);

        Release release = releaseRepository.findBySlug(releaseSlug);
        if(release == null) {
            logger.error("Release with slug [{}] does not exist", releaseSlug);
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

        logger.info("***** SUCCESSFULLY DELETED RELEASE WITH SLUG = {} *****", releaseSlug);
    }

    private String createReleaseSlug(String releaseName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(releaseName);

        int duplicateSlugs = releaseRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }
}
