package com.tradekraftcollective.microservice.validator;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.Video;
import com.tradekraftcollective.microservice.repository.IVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@Slf4j
@Component
public class VideoValidator {

    private final int MIN_PREVIEW_TIME = 10000;

    private final int MAX_PREVIEW_TIME = 60000;

    private final String YOUTUBE_URL_REGEX = "http(?:s?):\\/\\/(?:www\\.)?youtu(?:be\\.com\\/watch\\?v=|\\.be\\/)([\\w\\-\\_]*)(&(amp;)?\u200C\u200B[\\w\\?\u200C\u200B=]*)?";

    @Autowired
    IVideoRepository videoRepository;

    public void validateVideo(Video video, MultipartFile videoFile) {
        validateVideoFile(videoFile);
        validateVideoFeature(video, true);
        validateVideoLink(video);
    }

    public void validateVideo(Video video) {
        validateVideoUploadOrVideoLink(video,false);
        validateVideoFeature(video, false);
        validateVideoLink(video);
    }

    private void validateVideoFeature(Video video, boolean hasMultipart) {
        if(video.isFeatured()) {
            if (!hasUploadFile(video, hasMultipart)) {
                log.error("Video cannot be featured without an uploaded video file.");
                throw new ServiceException(ErrorCode.INVALID_VIDEO, "Video cannot be featured without a video file.");
            }

            if (video.getVideoPreviewStartTime() == null || video.getVideoPreviewEndTime() == null) {
                log.error("Featured video must contain an start and end time to create preview clip.");
                throw new ServiceException(ErrorCode.INVALID_VIDEO, "Featured video must contain an start and end time to create preview clip.");
            }

            if (video.getSong() == null && video.getExternalUrl() == null) {
                log.error("Featured videos must be linked to a TradeKraft song or have an external link.");
                throw new ServiceException(ErrorCode.INVALID_VIDEO, "Featured videos must be linked to a TradeKraft song or have an external link.");
            }

            validateVideoPreviewTime(video.getVideoPreviewStartTime(), video.getVideoPreviewEndTime());
        }
    }

    private void validateVideoPreviewTime(int startTime, int endTime) {
        if((endTime - startTime) < MIN_PREVIEW_TIME) {
            log.error("Video preview must be at least 10 seconds long.");
            throw new ServiceException(ErrorCode.INVALID_VIDEO_PREVIEW_TIME, "Video preview must be at least 10 seconds long.");
        }

        if((endTime - startTime) > MAX_PREVIEW_TIME) {
            log.error("Video preview can only be 60 seconds max");
            throw new ServiceException(ErrorCode.INVALID_VIDEO_PREVIEW_TIME, "Video preview can only be 60 seconds max");
        }

        // Gotta validate if start time is more than zero and end time is less than the length of the video
        // Start time is easy but need video length from ffmpeg to validate end time
    }

    private void validateVideoLink(Video video) {
        if(!(video.getYoutubeUrl() == null || video.getYoutubeUrl().isEmpty()) &&
                !Pattern.compile(YOUTUBE_URL_REGEX).matcher(video.getYoutubeUrl()).find()) {
            log.error("Not a valid YouTube url.");
            throw new ServiceException(ErrorCode.INVALID_VIDEO_EXTERNAL_LINK, "Not [" + video.getYoutubeUrl() + "] is not a valid YouTube url.");
        }
    }

    private void validateVideoFile(MultipartFile videoFile) {

    }

    private void validateVideoUploadOrVideoLink(Video video, boolean hasMultipart) {
        if(!hasUploadFile(video, hasMultipart) && video.getYoutubeUrl() == null) {
            log.error("Video must either have a video upload or external video link.");
            throw new ServiceException(ErrorCode.INVALID_VIDEO, "Video must either have a video upload or external video link.");
        }
    }

    private boolean hasUploadFile(Video video, boolean hasMultipart) {
        return (hasMultipart || video.getVideoFile() != null);
    }


    public void validateVideoSlug(String videoSlug) {
        if(videoRepository.findBySlug(videoSlug) == null) {
            log.error("Video with slug [{}] does not exist", videoSlug);
            throw new ServiceException(ErrorCode.INVALID_VIDEO_SLUG, "Video with slug [" + videoSlug + "] does not exist");
        }
    }
}
