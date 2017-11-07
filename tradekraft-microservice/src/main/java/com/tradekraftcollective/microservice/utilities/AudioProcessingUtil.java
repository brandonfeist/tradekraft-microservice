package com.tradekraftcollective.microservice.utilities;

import com.tradekraftcollective.microservice.persistence.entity.Artist;
import com.tradekraftcollective.microservice.persistence.entity.Release;
import com.tradekraftcollective.microservice.persistence.entity.Song;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.strategies.MetaData;
import com.tradekraftcollective.microservice.strategy.AudioFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by brandonfeist on 10/23/17.
 */
@Slf4j
@Component
public class AudioProcessingUtil {
    private final String DURATION_REGEX = "^(\\s{2})(Duration\\:\\s)(\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{2})(.+)$";

    @Inject
    AmazonS3Service amazonS3Service;

    public String processAudioAndUpload(List<AudioFormat> audioFormats, Release release, Song song, String uploadPath, MultipartFile audioFile) {
        String originalFileName = audioFile.getOriginalFilename();
        String fileNameNoExtension = FilenameUtils.getBaseName(originalFileName);

        List<MetaData> metaDataList = retrieveMetaDataList(release, song);

        try {
            for(AudioFormat audioFormat : audioFormats) {
                if(audioFormat.getFileName().equals("original")) {
                    log.debug("Uploading original audio file [{}]", originalFileName);

                    File tmpFile = convertAudio(song, fileNameNoExtension, audioFile, audioFormat, metaDataList);

                    amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

                    tmpFile.delete();
                } else {
                    log.debug("Uploading {} audio file [{}]", audioFormat.getFileName(), originalFileName);

                    String updatedFileNameNoExtension = audioFormat.getFileName() + "_" + fileNameNoExtension;
                    File tmpFile = convertAudio(song, updatedFileNameNoExtension, audioFile, audioFormat, metaDataList);

                    amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

                    tmpFile.delete();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return fileNameNoExtension;
    }

    private File convertAudio(Song song, String fileName, MultipartFile inputAudioFile, AudioFormat audioFormat, List<MetaData> metaDataList) throws IOException {
        String duration;
        String originalFileName = inputAudioFile.getOriginalFilename();

        File tmpAudioFile = createTempAudioFile(originalFileName, inputAudioFile);

        File outputFile =  new File(fileName + "." + audioFormat.getExtension());

        // Make a check to make sure extension and audio format are the same and are also valid.

        log.info("Converting audio file {} to {}", tmpAudioFile.getName(), outputFile.getName());
        String[] ffmpegCommand = {"ffmpeg", "-i", tmpAudioFile.getAbsolutePath(), "-c:a", audioFormat.getCodec(),
            "-ac", audioFormat.getChannels(), "-b:a", audioFormat.getBitRate()};
        String[] metaDataCommand = createMetaDataString(metaDataList);
        String[] outputCommand = {"-y", "-f", audioFormat.getFormat(), outputFile.getAbsolutePath()};
        String[] command = ArrayUtils.addAll(ArrayUtils.addAll(ffmpegCommand, metaDataCommand), outputCommand);

        Process p = Runtime.getRuntime().exec(command);

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

    private List<MetaData> retrieveMetaDataList(Release release, Song song) {
        List<MetaData> returnMetaDataList = new ArrayList<>();

        returnMetaDataList.add(new MetaData("title", song.getName()));
        returnMetaDataList.add(new MetaData("album", release.getName()));
        returnMetaDataList.add(new MetaData("genre", song.getGenre().getName()));
        returnMetaDataList.add(new MetaData("publisher", "TradeKraft"));
        returnMetaDataList.add(new MetaData("track", song.getTrackNumber().toString()));
        returnMetaDataList.add(new MetaData("author", getArtistMetaDataString(song)));
        returnMetaDataList.add(new MetaData("artist", getArtistMetaDataString(song)));

        return returnMetaDataList;
    }

    private String getArtistMetaDataString(Song song) {
        String artistString = "";
        Object[] artists = song.getArtists().toArray();

        // Need to build upon the artist string function to know when to do feat. vs. &
        for(int artistIndex = 0; artistIndex < artists.length; artistIndex++) {
            if(artistIndex == 0) {
                artistString += ((Artist) artists[artistIndex]).getName();
            } else {
                artistString += "& " + ((Artist) artists[artistIndex]).getName();
            }
        }

        return artistString;
    }

    private String[] createMetaDataString(List<MetaData> metaDataList) {
        List<String> artistMetadataList = new ArrayList<>();

        for(MetaData metaData : metaDataList) {
            artistMetadataList.add("-metadata");
            artistMetadataList.add((metaData.getKey() + "=" + metaData.getValue()));
        }

        return artistMetadataList.toArray(new String[artistMetadataList.size()]);
    }
}
