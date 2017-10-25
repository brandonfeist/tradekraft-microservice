package com.tradekraftcollective.microservice.strategy;

import lombok.Data;

/**
 * Created by brandonfeist on 10/25/17.
 */
@Data
public class AudioFormat {
    private String fileName;

    private String codec;

    private String channels;

    private String bitRate;

    private String format;

    private String extension;

    public AudioFormat(String fileName, String codec, String channels, String bitRate, String format, String extension) {
        this.fileName = fileName;
        this.codec = codec;
        this.channels = channels;
        this.bitRate = bitRate;
        this.format = format;
        this.extension = extension;
    }
}
