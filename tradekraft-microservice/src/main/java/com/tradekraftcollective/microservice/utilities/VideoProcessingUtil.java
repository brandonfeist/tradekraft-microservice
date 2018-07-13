package com.tradekraftcollective.microservice.utilities;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.persistence.entity.Video;
import com.tradekraftcollective.microservice.persistence.entity.media.VideoFile;
import com.tradekraftcollective.microservice.persistence.entity.media.VideoThumbnail;
import com.tradekraftcollective.microservice.service.AmazonS3Service;
import com.tradekraftcollective.microservice.strategy.VideoFormat;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class VideoProcessingUtil {

    private final int INITIAL_VIDEO_FRAME = 60;

    @Value("${vcap.services.ffmpeg.config.path}")
    private String FFMPEG_PATH;

    @Value("${vcap.services.ffmpeg.config.probe-path}")
    private String FFMPEG_PROBE_PATH;

    @Autowired
    AmazonS3Service amazonS3Service;

    public HashMap<String, VideoFile> processVideoHashAndUpload(
            List<VideoFormat> videoFormats, String uploadPath, String AWSUrl, MultipartFile videoFile,
            double videoQuality, Video video, int previewStartTime, int previewEndTime
    ) {
        HashMap<String, VideoFile> returnMap = new HashMap<>();

        Slugify slug = new Slugify();

        String originalFileName = videoFile.getOriginalFilename();

        String fileNameNoExtension = slug.slugify(FilenameUtils.getBaseName(originalFileName));

        try {
            for (VideoFormat videoFormat : videoFormats) {
                log.debug("Uploading video file [{}], [{}]", originalFileName, videoFormat.getFileName());

                String updatedFileNameNoExtension = videoFormat.getFileName() + "_" + fileNameNoExtension;
                File tmpFile = convertVideo(updatedFileNameNoExtension, videoFile, videoFormat);

                tmpFile.createNewFile();

                amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

                VideoFile newVideoFile = new VideoFile(videoFormat.getFileName(), AWSUrl + tmpFile.getName());

                returnMap.put(videoFormat.getFileName(), newVideoFile);

                tmpFile.delete();

                if(video.isFeatured() || true) {
                    log.debug("Creating video preview file from [{}] to [{}]", previewStartTime, previewEndTime);

                    String updatedPreviewFileNameNoExtension = videoFormat.getFileName() + "_" + fileNameNoExtension + "_preview";
                    tmpFile = createVideoPreview(video, updatedPreviewFileNameNoExtension, videoFile, videoFormat, previewStartTime, previewEndTime);

                    amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

                    VideoFile newVideoPreviewFile = new VideoFile((videoFormat.getFileName() + "_preview"), AWSUrl + tmpFile.getName());

                    returnMap.put((videoFormat.getFileName() + "_preview"), newVideoPreviewFile);

                    tmpFile.delete();
                }

            }
        } catch(IOException e) {
            log.error(e.toString());
        }

        return returnMap;
    }

    public HashMap<String, VideoThumbnail> getVideoThumbnailHash(String uploadPath, String AWSUrl, MultipartFile videoFile) {
        HashMap<String, VideoThumbnail> returnMap = new HashMap<>();

        try {
            Slugify slug = new Slugify();

            String originalFileName = videoFile.getOriginalFilename();

            String fileNameNoExtension = slug.slugify(FilenameUtils.getBaseName(originalFileName));

            File tmpVideoFile = createTempVideoFile(originalFileName, videoFile);

            File outputFile =  new File(fileNameNoExtension + ".jpg");

            FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);
            FFprobe ffprobe = new FFprobe(FFMPEG_PROBE_PATH);

            int frame = INITIAL_VIDEO_FRAME;
//            if(video.getVideoPreviewStartTime() != null && video.getVideoPreviewEndTime() != null) {
//                int difference = video.getVideoPreviewEndTime() - video.getVideoPreviewStartTime();
//                frame = (video.getVideoPreviewEndTime() - (difference / 2)) / 30;
//            }

            FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(tmpVideoFile.getName())
                .addOutput(outputFile.getName())
                .setFrames(1)
                .setVideoFilter("select='gte(n\\," + frame + ")',scale=1920:-1")
                .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            executor.createJob(builder).run();

            tmpVideoFile.delete();

            log.debug("Uploading video thumbnail");

            amazonS3Service.upload(outputFile, uploadPath, outputFile.getName());

            BufferedImage bufferedImage = ImageIO.read(outputFile);

            VideoThumbnail videoThumbnail = new VideoThumbnail("jpg", AWSUrl + outputFile.getName(),
                    bufferedImage.getWidth(), bufferedImage.getHeight());

            returnMap.put("original", videoThumbnail);

            outputFile.delete();

            return returnMap;
        } catch(IOException e) {
            log.error(e.toString());
        }

        return returnMap;
    }

    private File convertVideo(String fileNameNoExtension, MultipartFile videoFile, VideoFormat videoFormat) {
        try {
            String originalFileName = videoFile.getOriginalFilename();

            File tmpVideoFile = createTempVideoFile(originalFileName, videoFile);

            File outputFile =  new File(fileNameNoExtension + "." + videoFormat.getExtension());

            FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);
            FFprobe ffprobe = new FFprobe(FFMPEG_PROBE_PATH);

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(tmpVideoFile.getName())
                    .overrideOutputFiles(true)
                    .addOutput(outputFile.getName())
                    .setFormat(videoFormat.getFormat())
//                    .setTargetSize(250_000)  // Aim for a 250KB file
                    .setVideoQuality(1.0)
                    .setConstantRateFactor(12)

                    .disableSubtitle()

                    .setVideoCodec(videoFormat.getCodec())

                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            // Run a one-pass encode
            executor.createJob(builder).run();

            // Or run a two-pass encode (which is slower at the cost of better quality)
//            executor.createTwoPassJob(builder).run();

            tmpVideoFile.delete();

            return outputFile;
        } catch(IOException e) {

        }

        return null;
    }

    private File createVideoPreview(Video video, String fileNameNoExtension, MultipartFile videoFile, VideoFormat videoFormat, int startTimeMilli, int endTimeMilli) {
        try {
            String originalFileName = videoFile.getOriginalFilename();

            File tmpVideoFile = createTempVideoFile(originalFileName, videoFile);

            File outputFile =  new File(fileNameNoExtension + "." + videoFormat.getExtension());

            FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);

            FFprobe ffprobe = new FFprobe(FFMPEG_PROBE_PATH);

            int duration = endTimeMilli - startTimeMilli;

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(tmpVideoFile.getName())
                    .overrideOutputFiles(true)
                    .addOutput(outputFile.getName())
                    .setStartOffset(startTimeMilli, TimeUnit.MILLISECONDS)
                    .setDuration(duration, TimeUnit.MILLISECONDS)
                    .setFormat(videoFormat.getFormat())

                    .disableSubtitle()

                    .setVideoCodec(videoFormat.getCodec())

                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);

            executor.createJob(builder).run();

            tmpVideoFile.delete();

            return outputFile;
        } catch(IOException e) {

        }

        return null;
    }

    private File createTempVideoFile(String fileName, MultipartFile videoFile) throws IOException {
        File tmpAudioFile = new File(fileName);

        tmpAudioFile.createNewFile();

        InputStream in = videoFile.getInputStream();

        FileOutputStream out = new FileOutputStream(tmpAudioFile);

        byte[] buffer = new byte[4096];
        int len;
        while ((len = in.read(buffer, 0, buffer.length)) > 0) {
            out.write(buffer, 0, len);
        }

        in.close();

        out.close();

        return tmpAudioFile;
    }
}
