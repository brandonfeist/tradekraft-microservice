package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
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
            finalSongSet.add(songManagementService.createSong(song, songFileHashMap.get(song.getSongFile())));
        }

        release.setSongs(finalSongSet);

        Release returnRelease = releaseRepository.save(release);

        stopWatch.stop();

        logger.info("***** SUCCESSFULLY CREATED RELEASE WITH SLUG = {} AND NUMBER OF SONG = {} *****", returnRelease.getSlug(), returnRelease.getSongs().size());

        return returnRelease;
    }

    private String createReleaseSlug(String releaseName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(releaseName);

        int duplicateSlugs = releaseRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }
}
