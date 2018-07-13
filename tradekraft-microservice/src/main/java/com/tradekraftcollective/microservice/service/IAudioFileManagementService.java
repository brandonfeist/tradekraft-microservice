package com.tradekraftcollective.microservice.service;

import com.tradekraftcollective.microservice.persistence.entity.media.AudioFile;
import org.springframework.web.multipart.MultipartFile;

public interface IAudioFileManagementService {

    /**
     * Uploads and starts the transcoding job for a given audio file.
     *
     * @param audioFile File to be uploaded.
     * @param whitelistedFormats Audio file formats that are aloud.
     * @return AudioFile object with Zencoder jobId.
     */
    AudioFile uploadAudioFile(MultipartFile audioFile, String[] whitelistedFormats);
}
