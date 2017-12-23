package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IVideoManagementService {
//    Page<Video> getVideos (int page, int pageSize, String sortField, String sortOrder, String searchQuery, String genreQuery, String typeQuery);

    Video getRandomFeatureVideo();

    Video createVideo(Video video, MultipartFile videoFile);

    Video createVideo(Video video);

    void deleteVideo(String videoSlug);
}
