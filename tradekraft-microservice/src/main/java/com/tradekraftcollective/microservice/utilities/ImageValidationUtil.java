package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class ImageValidationUtil {
    private final Map<String, String> imageExtensionWhitelist;

    /**
     * Default constructor for ImageValidationUtil, sets valid image extensions map.
     */
    public ImageValidationUtil() {
        imageExtensionWhitelist = new HashMap<>();
        imageExtensionWhitelist.put("jpg", "jpg"); imageExtensionWhitelist.put("jpeg", "jpeg");
        imageExtensionWhitelist.put("gif", "gif"); imageExtensionWhitelist.put("png", "png");
    }

    /**
     * Validates if given image file is larger or equal to the given height and width.
     *
     * @param width The minimum image width
     * @param height The minimum image height
     * @param image The BufferedImage to be validated
     */
    public void minimumImageSize(int width, int height, BufferedImage image) {
        if((width > 0 && image.getWidth() < width) || (height > 0 && image.getHeight() < height)) {
            log.error("Invalid image dimensions ({}, {}) must be at least ({}, {})",
                    image.getWidth(), image.getHeight(), width, height);

            throw new ServiceException(ErrorCode.INVALID_IMAGE_DEMINSIONS,
                    String.format("image dimensions must be at least [%s, %s]", width, height));
        }
    }

    /**
     * Checks MultipartFile to validate if it has a valid image extension.
     *
     * @param image The multipart file to be validated
     */
    public void validateImageExtension(MultipartFile image) {
        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(image.getInputStream());

            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
            while (imageReaders.hasNext()) {
                ImageReader reader = imageReaders.next();
                if (!imageExtensionWhitelist.containsKey(reader.getFormatName().toLowerCase())) {
                    log.error("Invalid image extension.");
                    throw new ServiceException(ErrorCode.INVALID_IMAGE_EXTENSION, "valid extensions are (jpg, jpeg, gif, or png)");
                }
            }
        } catch(IOException | IllegalArgumentException e) {
            log.error("Invalid image extension.");
            throw new ServiceException(ErrorCode.INVALID_IMAGE_EXTENSION, "valid extensions are (jpg, jpeg, gif, or png)");
        }
    }
}
