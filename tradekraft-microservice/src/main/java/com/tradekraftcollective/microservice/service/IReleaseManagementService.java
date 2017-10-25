package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.Release;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * Created by brandonfeist on 10/22/17.
 */
public interface IReleaseManagementService {

    Release createRelease(Release release, MultipartFile imageFile, MultipartFile[] songFiles, StopWatch stopWatch);
}
