package com.tradekraftcollective.microservice.utilities;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

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
    public void resizeToLimit(int width, int height, MultipartFile multipartFile) throws IOException {
        Image image = ImageIO.read(multipartFile.getInputStream());
    }

    /**
     * Resize the image to fit within the specified dimensions while retaining the original aspect ratio.
     * The image may be shorter or narrower than specified in the smaller dimension but will not be larger than the
     * specified values.
     * @param width int imageWidth fit
     * @param height int imageHeight fit
     */
    public void resizeToFit(int width, int height) {

    }

    public void reduceImageQuality(float percentageToReduceBy) {

    }
}
