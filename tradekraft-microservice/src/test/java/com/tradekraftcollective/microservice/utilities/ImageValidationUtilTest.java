package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@RunWith(MockitoJUnitRunner.class)
public class ImageValidationUtilTest {

    @InjectMocks
    private ImageValidationUtil imageValidationUtil;

    private BufferedImage testImage;

    @Test
    public void imageValidationWithValidImageTest() throws IOException {

        testImage = getBufferedImageFromResource("/images/test-image.jpg");

        imageValidationUtil.minimumImageSize(1024, 1024, testImage);
    }

    @Test(expected = ServiceException.class)
    public void imageValidationWithInvalidImageWidthTest() throws IOException {
        testImage = getBufferedImageFromResource("/images/test-image.jpg");

        imageValidationUtil.minimumImageSize(1080, 1024, testImage);
    }

    @Test(expected = ServiceException.class)
    public void imageValidationWithInvalidImageHeightTest() throws IOException {

        testImage = getBufferedImageFromResource("/images/test-image.jpg");

        imageValidationUtil.minimumImageSize(1024, 1080, testImage);
    }

    @Test
    public void imageValidationWithNoMinImageHeightTest() throws IOException {

        testImage = getBufferedImageFromResource("/images/test-image.jpg");

        imageValidationUtil.minimumImageSize(1024, -1, testImage);
    }

    @Test
    public void imageValidationWithNoMinImageWidthTest() throws IOException {

        testImage = getBufferedImageFromResource("/images/test-image.jpg");

        imageValidationUtil.minimumImageSize(-1, 1024, testImage);
    }

    private BufferedImage getBufferedImageFromResource(String destination) throws IOException {

        InputStream input = new ClassPathResource(destination).getInputStream();

        return ImageIO.read(input);
    }
}
