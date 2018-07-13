package com.tradekraftcollective.microservice.service.impl;

import com.brightcove.zencoder.client.ZencoderClientException;
import com.brightcove.zencoder.client.response.ZencoderCreateJobResponse;
import com.brightcove.zencoder.client.response.ZencoderJobDetail;
import com.tradekraftcollective.microservice.exception.ErrorCode;
import com.tradekraftcollective.microservice.exception.ServiceException;
import com.tradekraftcollective.microservice.persistence.entity.media.AudioFile;
import com.tradekraftcollective.microservice.repository.IAudioFileRepository;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.service.IAudioFileManagementService;
import com.tradekraftcollective.microservice.service.ZencoderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class AudioFileManagementService implements IAudioFileManagementService {

    private static final String AWS_URL = "https://s3.amazonaws.com/tradekraft-assets/uploads/audio/";
    private static final String AUDIO_AWS_KEY = "uploads/audio/";
    private static final String[] AUDIO_FORMATS = {
            "mp3",
            "ogg"
    };

    @Autowired
    private IAudioFileRepository audioFileRepository;

    @Autowired
    private ZencoderService zencoderService;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Value("${vcap.services.amazon-aws.credentials.bucket}")
    private String bucket;

    /**
     * {@inheritDoc}
     */
    @Override
    public AudioFile uploadAudioFile(MultipartFile audioFile, String[] whitelistedFormats) {
        log.info("Uploading audio file [{}]", audioFile.getOriginalFilename());

        String fileName = audioFile.getOriginalFilename();
        String fileNameNoExtension = FilenameUtils.removeExtension(audioFile.getOriginalFilename());

        // Verify size and audio file formats(wAV and FLAC)
        // If size int is -1 ignore, can be any size
        // audioFileValidator.validateAudioFile(audioFile, whitelistedFormats);

        UUID uuid = UUID.randomUUID();

        amazonS3Service.upload(audioFile, AUDIO_AWS_KEY + uuid.toString() + "/", fileName);

        AudioFile returnAudioFile = new AudioFile(uuid, AWS_URL + uuid.toString() + "/" + fileName);

        ZencoderJobDetail zencoderJobDetail;
        try {
            zencoderJobDetail = zencoderService.transcodeAudio(
                    getS3BucketPath(uuid) + fileName,
                    getS3BucketPath(uuid) + fileNameNoExtension,
                    AUDIO_FORMATS
            );

            returnAudioFile.setDuration(getAudioFileDuration(audioFile));

            returnAudioFile = audioFileRepository.save(returnAudioFile);

            returnAudioFile.setZencoderJobId(zencoderJobDetail.getId());

        } catch(ZencoderClientException ex) {
            ex.printStackTrace();
        }

        log.info("***** SUCCESSFULLY UPLOADED AUDIO FILE AT = {} *****", returnAudioFile.getLink());

        return returnAudioFile;
    }

    /**
     * Retrieves the duration in seconds of a given multi part audio file.
     *
     * @param audioFile The audio file to be analyzed
     * @return a double representing the duration of the audio file in seconds
     */
    private Double getAudioFileDuration(MultipartFile audioFile) {
        Double durationInSeconds;

        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        AudioInputStream audioInputStream = null;
        try {
            inputStream = audioFile.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            durationInSeconds = (frames + 0.0) / format.getSampleRate() / (format.getSampleSizeInBits() / 8.0) / format.getChannels();
        } catch (IOException e) {
            log.error("IO Exception", e);

            e.printStackTrace();

            throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            log.error("Unsupported audio file used", e);

            e.printStackTrace();

            throw new ServiceException(ErrorCode.UNSUPPORTED_AUDIO_FORMAT, "This audio file format is unsupported.");
        } finally {
            try {
                inputStream.close();
                bufferedInputStream.close();
                audioInputStream.close();
            } catch (IOException e) {
                log.error("IO Exception", e);

                e.printStackTrace();

                throw new ServiceException(ErrorCode.IO_EXCEPTION, e.getMessage());
            }
        }

        log.info("Audio File Duration: {}", durationInSeconds);

        return durationInSeconds;
    }

    /**
     * Creates an AWS bucket path for audio using the given UUID.
     *
     * @param uuid UUID of audio file
     * @return String of AWS bucket path
     */
    private String getS3BucketPath(UUID uuid) {
        return "s3://" + bucket + "/" + AUDIO_AWS_KEY + uuid.toString() + "/";
    }
}
