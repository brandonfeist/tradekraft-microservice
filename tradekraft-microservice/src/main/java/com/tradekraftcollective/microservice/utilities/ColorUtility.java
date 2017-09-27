package com.tradekraftcollective.microservice.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * Created by brandonfeist on 9/26/17.
 */
@Component
public class ColorUtility {
    private static final Logger logger = LoggerFactory.getLogger(ColorUtility.class);

    public int[] hexToRgb(String hex) {
        int[] rgb = {0, 0, 0};

        if(!hex.startsWith("#")) {
            hex = "#".concat(hex);
        }

        Color color = Color.decode(hex);

        rgb[0] = color.getRed();
        rgb[1] = color.getGreen();
        rgb[2] = color.getBlue();

        logger.info("Converted Hex: {} to RGB: [{},{},{}]", hex, rgb[0], rgb[1], rgb[2]);
        return rgb;
    }

    public int rgbToHue(int[] rgb) {
        int red = rgb[0];
        int green = rgb[1];
        int blue = rgb[2];

        float min = Math.min(Math.min(red, green), blue);
        float max = Math.max(Math.max(red, green), blue);

        float hue;
        if (max == red) {
            hue = (green - blue) / (max - min);

        } else if (max == green) {
            hue = 2f + (blue - red) / (max - min);

        } else {
            hue = 4f + (red - green) / (max - min);
        }

        hue = hue * 60;
        if (hue < 0) hue = hue + 360;

        logger.info("RGB: [{},{},{}] converted to Hue: [{}]", rgb[0], rgb[1], rgb[2], Math.round(hue));
        return Math.round(hue);
    }
}
