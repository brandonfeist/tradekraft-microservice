package com.tradekraftcollective.microservice.utilities;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * Created by brandonfeist on 9/8/17.
 */
@Component
public class ImageProcessingUtil {
    /**
     * Resize the image to fit within the specified dimensions while retaining the original aspect ratio.
     * Will only resize the image if it is larger than the specified dimensions.
     * The resulting image may be shorter or narrower than specified in the smaller dimension but will not be larger
     * than the specified values.
     * @param width int imageWidth limit
     * @param height int imageHeight limit
     */
    public File resizeToLimit(int width, int height, double quality, MultipartFile multipartFile, File newFile) {
        try {
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());

            Thumbnails.of(image)
                    .size(width, height)
                    .keepAspectRatio(true)
                    .outputQuality(quality)
                    .toFile(newFile);

        } catch(IOException e) {
            e.printStackTrace();
        }

        return newFile;
    }

    /**
     * Resize the image to fit within the specified dimensions while retaining the original aspect ratio.
     * The image may be shorter or narrower than specified in the smaller dimension but will not be larger than the
     * specified values.
     * @param width int imageWidth fit
     * @param height int imageHeight fit
     */
    public MultipartFile resizeToFit(int width, int height, MultipartFile multipartFile) {
        return null;
    }

    public void reduceImageQuality(float percentageToReduceBy) {

    }

    public String getImageExtension(MultipartFile image) {
        String fileExtension = null;

        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(image.getInputStream());

            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(imageInputStream);
            while (imageReaders.hasNext()) {
                ImageReader reader = imageReaders.next();
                fileExtension = reader.getFormatName().toLowerCase();
            }
        } catch(IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return fileExtension;
    }
}
