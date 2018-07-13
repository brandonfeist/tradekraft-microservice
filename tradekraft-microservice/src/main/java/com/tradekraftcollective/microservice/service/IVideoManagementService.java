package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IVideoManagementService {
    Page<Video> getVideos (int page, int pageSize, String sortField, String sortOrdery);

    Video getRandomFeatureVideo();

    Video createVideo(Video video);

    Video uploadVideoFile(String videoSlug, MultipartFile videoFile, int previewStartMilli, int previewEndMilli);

    void deleteVideo(String videoSlug);
}
