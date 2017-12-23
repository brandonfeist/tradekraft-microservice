package com.tradekraftcollective.microservice.service.impl;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Video;
import com.tradekraftcollective.microservice.repository.IReleaseRepository;
import com.tradekraftcollective.microservice.repository.IVideoRepository;
import com.tradekraftcollective.microservice.service.IVideoManagementService;
import com.tradekraftcollective.microservice.utilities.VideoProcessingUtil;
import com.tradekraftcollective.microservice.validator.VideoValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class VideoManagementService implements IVideoManagementService {

    @Autowired
    IVideoRepository videoRepository;

    @Autowired
    IReleaseRepository releaseRepository;

    @Autowired
    VideoProcessingUtil videoProcessingUtil;

    @Autowired
    VideoValidator videoValidator;

    @Override
    public Video getRandomFeatureVideo() {
        log.info("Getting a random feature video.");

        Video randomVideo = videoRepository.findRandomFeatureVideo();

        log.info("***** SUCCESSFULLY GOT RANDOM VIDEO WITH NAME = {} *****", randomVideo.getName());

        return randomVideo;
    }

    @Override
    public Video createVideo(Video video, MultipartFile videoFile) {
        // Use ffmpeg to get picture to use while vid is loading
        // Use ffmpeg to convert to mp4 and webm
        log.info("Create video, name: {}", video.getName());

        videoValidator.validateVideo(video, videoFile);

        video.setRelease(findAndSetVideoRelease(video));

        video.setSlug(createVideoSlug(video.getName()));

        video.setVideoFile(videoProcessingUtil.processVideoAndUpload(video.getVideoFormats(),
                video,
                (video.VIDEO_UPLOAD_PATH + video.getSlug() + "/"),
                videoFile));

        Video returnVideo = videoRepository.save(video);

        log.info("***** SUCCESSFULLY CREATED VIDEO WITH SLUG = {} *****", returnVideo.getSlug());

        return returnVideo;
    }

    @Override
    public Video createVideo(Video video) {
        log.info("Create video, name: {}", video.getName());

        videoValidator.validateVideo(video);

        video.setRelease(findAndSetVideoRelease(video));

        video.setSlug(createVideoSlug(video.getName()));

        Video returnVideo = videoRepository.save(video);

        log.info("***** SUCCESSFULLY CREATED VIDEO WITH SLUG = {} AND LINK = {} *****", returnVideo.getSlug(), returnVideo.getVideoFile());

        return returnVideo;
    }

    @Override
    public void deleteVideo(String videoSlug) {
        log.info("Delete video, slug: {}", videoSlug);

        videoValidator.validateVideoSlug(videoSlug);

        // Check if video file exists to delete or if its just an external link
//        ObjectListing directoryImages = amazonS3Service.getDirectoryContent((ARTIST_IMAGE_PATH + artistSlug + "/"), null);
//        for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
//            amazonS3Service.delete(summary.getKey());
//        }

        videoRepository.deleteBySlug(videoSlug);

        log.info("***** SUCCESSFULLY DELETED VIDEO WITH SLUG = {} *****", videoSlug);
    }

    private String createVideoSlug(String videoName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(videoName);

        int duplicateSlugs = videoRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }

    private Release findAndSetVideoRelease(Video video) {
        Release videoRelease = video.getRelease();

        if(videoRelease != null) {
            Release checkedRelease = releaseRepository.findBySlug(videoRelease.getSlug());

            if (checkedRelease == null) {
                log.error("Release with slug [{}] does not exist.", checkedRelease.getSlug());
                throw new ServiceException(ErrorCode.INVALID_RELEASE_SLUG, "release with slug [" + checkedRelease.getSlug() + "] does not exist.");
            }

            return checkedRelease;
        }

        return null;
    }
}
