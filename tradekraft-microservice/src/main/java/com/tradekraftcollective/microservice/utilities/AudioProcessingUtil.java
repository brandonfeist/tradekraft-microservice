package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.strategy.AudioFormat;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by brandonfeist on 10/23/17.
 */
@Component
public class AudioProcessingUtil {
    private static Logger logger = LoggerFactory.getLogger(AudioProcessingUtil.class);

    private final String DURATION_REGEX = "^(\\s{2})(Duration\\:\\s)(\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{2})(.+)$";

    @Inject
    AmazonS3Service amazonS3Service;

    public String processAudioAndUpload(List<AudioFormat> audioFormats, Song song, String uploadPath, MultipartFile audioFile) {
        String originalFileName = audioFile.getOriginalFilename();
        String fileNameNoExtension = FilenameUtils.getBaseName(originalFileName);

        try {
            for(AudioFormat audioFormat : audioFormats) {
                if(audioFormat.getFileName().equals("original")) {
                    logger.debug("Uploading original audio file [{}]", originalFileName);

                    File tmpFile = convertAudio(song, fileNameNoExtension, audioFile, audioFormat);

                    amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

                    tmpFile.delete();
                } else {
                    logger.debug("Uploading {} audio file [{}]", audioFormat.getFileName(), originalFileName);

                    String updatedFileNameNoExtension = audioFormat.getFileName() + "_" + fileNameNoExtension;
                    File tmpFile = convertAudio(song, updatedFileNameNoExtension, audioFile, audioFormat);

                    amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

                    tmpFile.delete();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return fileNameNoExtension;
    }

    private File convertAudio(Song song, String fileName, MultipartFile inputAudioFile, AudioFormat audioFormat) throws IOException {
        String duration;
        String originalFileName = inputAudioFile.getOriginalFilename();

        File tmpAudioFile = createTempAudioFile(originalFileName, inputAudioFile);

        File outputFile =  new File(fileName + "." + audioFormat.getExtension());

        /*
        // Make a check to make sure extension and audio format are the same and are also valid.
//        if(FilenameUtils.getExtension(fileName) != audioFormat)
        if(audioFormat.equals("ogg")) {
//            outputFile = new File(FilenameUtils.getBaseName(originalFileName) + ".ogg");
        } else if(audioFormat.equals("adts")) {
//            outputFile = new File(FilenameUtils.getBaseName(originalFileName) + ".m4a");
        } else {
//            return outputFile;
        }*/

        logger.info("Converting audio file {} to {}", tmpAudioFile.getName(), outputFile.getName());
        Process p = Runtime.getRuntime().exec("ffmpeg " +
                "-i " + tmpAudioFile.getAbsolutePath() + " " +
                "-c:a " + audioFormat.getCodec() + " -y " +
                "-ac " + audioFormat.getChannels() + " " +
                "-b:a " + audioFormat.getBitRate() + " -f " + audioFormat.getFormat() + " " +
                outputFile.getAbsolutePath());

        String ffmpegOutput;
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        Pattern pattern = Pattern.compile(DURATION_REGEX);
        Matcher matcher;

        while ((ffmpegOutput = stdError.readLine()) != null) {
            matcher = pattern.matcher(ffmpegOutput);

            if(matcher.find()) {
                duration = matcher.group(3);
                duration = duration.substring(0, (duration.length() - 3));

                if(duration.substring(0, 3).equals("00:")) {
                    duration = duration.substring(3, duration.length());
                }

                song.setDuration(duration);
            }
        }

        tmpAudioFile.delete();

        return outputFile;
    }

    private File createTempAudioFile(String fileName, MultipartFile audioFile) throws IOException {
        File tmpAudioFile = new File(fileName);

        tmpAudioFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(tmpAudioFile);

        fos.write(audioFile.getBytes());

        fos.close();

        return tmpAudioFile;
    }
}
