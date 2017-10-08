package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by brandonfeist on 9/9/17.
 */
@Component
public class ImageValidationUtil {
    private final Logger logger = LoggerFactory.getLogger(ImageValidationUtil.class);

    private final Map<String, String> imageExtensionWhitelist;

    public ImageValidationUtil() {
        imageExtensionWhitelist = new HashMap<>();
        imageExtensionWhitelist.put("jpg", "jpg"); imageExtensionWhitelist.put("jpeg", "jpeg");
        imageExtensionWhitelist.put("gif", "gif"); imageExtensionWhitelist.put("png", "png");
    }

    public void minimumImageSize(int width, int height, BufferedImage image) {
        if(image.getWidth() < width || image.getHeight() < height) {
            logger.error("Invalid image dimensions ({}, {}) must be at least ({}, {})",
                    image.getWidth(), image.getHeight(), width, height);
            throw new ServiceException(ErrorCode.INVALID_IMAGE_DEMINSIONS,
                    String.format("image dimensions must be at least [%s, %s]", width, height));
        }
    }

    public void validateImageExtension(MultipartFile image) {
        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(image.getInputStream());

            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
            while (imageReaders.hasNext()) {
                ImageReader reader = (ImageReader) imageReaders.next();
                if (!imageExtensionWhitelist.containsKey(reader.getFormatName().toLowerCase())) {
                    logger.error("Invalid image extension.");
                    throw new ServiceException(ErrorCode.INVALID_IMAGE_EXTENSION, "valid extensions are (jpg, jpeg, gif, or png)");
                }
            }
        } catch(IOException | IllegalArgumentException e) {
            logger.error("Invalid image extension.");
            throw new ServiceException(ErrorCode.INVALID_IMAGE_EXTENSION, "valid extensions are (jpg, jpeg, gif, or png)");
        }
    }
}
