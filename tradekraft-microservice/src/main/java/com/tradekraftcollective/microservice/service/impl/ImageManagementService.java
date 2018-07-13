package com.tradekraftcollective.microservice.service.impl;

import com.tradekraftcollective.microservice.persistence.entity.media.Image;
import com.tradekraftcollective.microservice.repository.IImageRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IImageManagementService;
import com.tradekraftcollective.microservice.utilities.ImageValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class ImageManagementService implements IImageManagementService {

    private static final String AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/image/";
    private static final String IMAGE_AWS_KEY = "uploads/image/";

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    private ImageValidationUtil imageValidationUtil;

    @Autowired
    private AmazonS3Service amazonS3Service;

    public Image uploadImage(MultipartFile imageFile, int minHeight, int minWidth, String[] whitelistedFormats) {
        log.info("Uploading image [{}]", imageFile.getOriginalFilename());

        String fileName = imageFile.getOriginalFilename();

        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());

            imageValidationUtil.minimumImageSize(minWidth, minHeight, bufferedImage);

        } catch (IOException e) {
            log.error("There was an issue with image file input stream", e);

            e.printStackTrace();
        }

        imageValidationUtil.validateImageExtension(imageFile);

        UUID uuid = UUID.randomUUID();

        amazonS3Service.upload(imageFile, IMAGE_AWS_KEY + uuid.toString() + "/", fileName);

        Image image = new Image(uuid, AWS_URL + uuid.toString() + "/" + fileName);

        image = imageRepository.save(image);

        log.info("***** SUCCESSFULLY UPLOADED IMAGE AT = {} *****", image.getLink());

        return image;
    }
}
