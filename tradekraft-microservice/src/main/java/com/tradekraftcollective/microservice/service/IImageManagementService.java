package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageManagementService {

    Image uploadImage(MultipartFile imageFile, int minHeight, int minWidth, String[] whitelistedFormats);
}
