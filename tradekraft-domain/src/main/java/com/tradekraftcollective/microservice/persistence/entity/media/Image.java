package com.tradekraftcollective.microservice.persistence.entity.media;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Image {

    public Image() {}

    public Image(String name, String link, Integer width, Integer height) {
        this.name = name;
        this.link = link;
        this.width = width;
        this.height = height;
    }

    private String name;

    private String link;

    private Integer width;

    private Integer height;
}
