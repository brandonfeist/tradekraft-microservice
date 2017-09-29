package com.tradekraftcollective.microservice.strategy;

import lombok.Data;

/**
 * Created by brandonfeist on 9/28/17.
 */
@Data
public class ImageSize {
    private String sizeName;

    private Integer width;

    private Integer height;

    public  ImageSize(String sizeName, Integer width, Integer height) {
        this.sizeName = sizeName;
        this.width = width;
        this.height = height;
    }
}
