package com.tradekraftcollective.microservice.service.impl;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.persistence.entity.Video;
import com.tradekraftcollective.microservice.persistence.entity.media.VideoFile;
import com.tradekraftcollective.microservice.persistence.entity.media.VideoThumbnail;
import com.tradekraftcollective.microservice.repository.ISongRepository;
import com.tradekraftcollective.microservice.repository.IVideoFileRepository;
import com.tradekraftcollective.microservice.repository.IVideoRepository;
import com.tradekraftcollective.microservice.repository.IVideoThumbnailRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IVideoManagementService;
import com.tradekraftcollective.microservice.utilities.VideoProcessingUtil;
import com.tradekraftcollective.microservice.validator.VideoValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class VideoManagementService implements IVideoManagementService {

    private static final String DESCENDING = "desc";

    @Autowired
    IVideoRepository videoRepository;

    @Autowired
    ISongRepository songRepository;

    @Autowired
    IVideoFileRepository videoFileRepository;

    @Autowired
    IVideoThumbnailRepository videoThumbnailRepository;

    @Autowired
    VideoProcessingUtil videoProcessingUtil;

    @Autowired
    AmazonS3Service amazonS3Service;

    @Autowired
    VideoValidator videoValidator;

    @Override
    public Page<Video> getVideos(int page, int pageSize, String sortField, String sortOrder) {
        log.info("Fetching videos, page: {} pageSize: {} sortField: {} sortOrder: {}", page, pageSize, sortField, sortOrder);

        Sort.Direction order = Sort.Direction.ASC;
        if(sortOrder != null && sortOrder.equalsIgnoreCase(DESCENDING)) {
            order = Sort.Direction.DESC;
        }

        PageRequest request = new PageRequest(page, pageSize, order, sortField);

        return videoRepository.findAll(request);
    }

    @Override
    public Video getRandomFeatureVideo() {
        log.info("Getting a random feature video.");

        Video randomVideo = videoRepository.findRandomFeatureVideo();

        if(randomVideo != null) {
            log.info("***** SUCCESSFULLY GOT RANDOM VIDEO WITH NAME = {} *****", randomVideo.getName());
        }

        return randomVideo;
    }

    @Override
    public Video createVideo(Video video) {
        log.info("Create video, name: {}", video.getName());

        videoValidator.validateVideo(video);

        video.setSong(findAndSetVideoSong(video));

        video.setSlug(createVideoSlug(video.getName()));

        Video returnVideo = videoRepository.save(video);

        log.info("***** SUCCESSFULLY CREATED VIDEO WITH SLUG = {} AND LINK = {} *****", returnVideo.getSlug(), returnVideo.getYoutubeUrl());

        return returnVideo;
    }

    @Override
    public Video uploadVideoFile(String videoSlug, MultipartFile videoFile, int previewStartMilli, int previewEndMilli) {
        log.info("Uploading video file for slug [{}]", videoSlug);

        videoValidator.validateVideoFile(videoFile);

        Video returnVideo = videoValidator.validateVideoSlug(videoSlug);

        deleteAllVideoFiles(returnVideo);

        HashMap<String, VideoFile> videoHash = videoProcessingUtil.processVideoHashAndUpload(returnVideo.getVideoFormats(),
                returnVideo.getAWSKey(), returnVideo.getAWSUrl(),
                videoFile, 1.0, returnVideo, previewStartMilli, previewEndMilli);

        saveVideosToRepo(videoHash, returnVideo);

        returnVideo.setVideoFiles(videoHash);

        HashMap<String, VideoThumbnail> videoThumbnailHash = videoProcessingUtil.getVideoThumbnailHash(
                returnVideo.getAWSKey(), returnVideo.getAWSUrl(), videoFile);

        saveVideoThumbnailToRepo(videoThumbnailHash, returnVideo);

        returnVideo.setVideoThumbnails(videoThumbnailHash);

        returnVideo = videoRepository.save(returnVideo);

        log.info("***** SUCCESSFULLY UPLOADED VIDEO FOR SLUG = {} *****", returnVideo.getSlug());

        return returnVideo;
    }

    private void saveVideosToRepo(HashMap<String, VideoFile> map, Video video) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            VideoFile videoFile = (VideoFile) pair.getValue();
            videoFile.setVideo(video);

            videoFileRepository.save((VideoFile) pair.getValue());

            it.remove();
        }
    }

    private void saveVideoThumbnailToRepo(HashMap<String, VideoThumbnail> map, Video video) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            VideoThumbnail videoThumbnail = (VideoThumbnail) pair.getValue();
            videoThumbnail.setVideo(video);

            videoThumbnailRepository.save((VideoThumbnail) pair.getValue());

            it.remove();
        }
    }

    private void deleteAllVideoFiles(Video video) {
        ObjectListing directoryImages = amazonS3Service.getDirectoryContent(video.getAWSKey(), null);
        for (S3ObjectSummary summary: directoryImages.getObjectSummaries()) {
            if(amazonS3Service.doesObjectExist(summary.getKey())) {
                amazonS3Service.delete(summary.getKey());
            }
        }

        Map<String, VideoFile> videoFiles = video.getVideoFiles();

        for (Map.Entry<String, VideoFile> entry : videoFiles.entrySet()) {
            log.info("Delete {}", entry.getValue());
            videoFileRepository.delete(entry.getValue());
        }

        Map<String, VideoThumbnail> videoThumbnails = video.getVideoThumbnails();

        for (Map.Entry<String, VideoThumbnail> entry : videoThumbnails.entrySet()) {
            log.info("Delete {}", entry.getValue().getName());
            videoThumbnailRepository.delete(entry.getValue());
        }
    }

    @Override
    public void deleteVideo(String videoSlug) {
        log.info("Delete video, slug: {}", videoSlug);

        Video video = videoValidator.validateVideoSlug(videoSlug);

        deleteAllVideoFiles(video);

        videoRepository.delete(video);

        log.info("***** SUCCESSFULLY DELETED VIDEO WITH SLUG = {} *****", videoSlug);
    }

    private String createVideoSlug(String videoName) {
        Slugify slug = new Slugify();
        String result = slug.slugify(videoName);

        int duplicateSlugs = videoRepository.findBySlugStartingWith(result).size();
        return duplicateSlugs > 0 ? result.concat("-" + (duplicateSlugs + 1)) : result;
    }

    private Song findAndSetVideoSong(Video video) {
        Song videoSong = video.getSong();

        if(videoSong != null) {
            log.info("Setting song with slug = [{}] to video.", videoSong.getSlug());

            Song checkedSong = songRepository.findBySlug(videoSong.getSlug());

            if (checkedSong == null) {
                log.error("Song with slug [{}] does not exist.", checkedSong.getSlug());
                throw new ServiceException(ErrorCode.INVALID_SONG_SLUG, "song with slug [" + checkedSong.getSlug() + "] does not exist.");
            }

            return checkedSong;
        }

        return null;
    }
}
