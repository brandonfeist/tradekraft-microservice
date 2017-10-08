package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.strategy.ImageSize;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by brandonfeist on 9/8/17.
 */
@Component
public class ImageProcessingUtil {
    private static Logger logger = LoggerFactory.getLogger(ImageProcessingUtil.class);

    @Inject
    AmazonS3Service amazonS3Service;

    public String processImageAndUpload(List<ImageSize> imageSizes, String uploadPath, MultipartFile imageFile, double imageQuality) {
        String fileName = imageFile.getOriginalFilename();

        try {
            for (ImageSize imageSize : imageSizes) {
                if (imageSize.getSizeName().equals("original")) {
                    logger.debug("Uploading original image size ({}, {})", imageSize.getWidth(), imageSize.getHeight());

                    File tmpFile = new File(fileName);

                    tmpFile.createNewFile();

                    amazonS3Service.upload(resizeToLimit(imageSize.getWidth(), imageSize.getHeight(), imageQuality, imageFile, tmpFile),
                            uploadPath, fileName);

                    tmpFile.delete();
                } else {
                    logger.debug("Uploading {} image size ({}, {})", imageSize.getSizeName(), imageSize.getWidth(), imageSize.getHeight());

                    File tmpFile = new File((imageSize.getSizeName() + "_" + fileName));

                    tmpFile.createNewFile();

                    amazonS3Service.upload(resizeToLimit(imageSize.getWidth(), imageSize.getHeight(), imageQuality, imageFile, tmpFile),
                            uploadPath, (imageSize.getSizeName() + "_" + fileName ));

                    tmpFile.delete();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }

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
