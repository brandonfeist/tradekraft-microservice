package com.tradekraftcollective.microservice.strategy;

import lombok.Data;

@Data
public class VideoFormat {
    private String fileName;

    private String codec;

    private String format;

    private String extension;

    public VideoFormat(String fileName, String codec, String format, String extension) {
        this.fileName = fileName;
        this.codec = codec;
        this.format = format;
        this.extension = extension;
    }
}
