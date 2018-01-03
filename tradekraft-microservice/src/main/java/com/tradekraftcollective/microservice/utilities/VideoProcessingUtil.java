package com.tradekraftcollective.microservice.utilities;

import com.github.slugify.Slugify;
import com.tradekraftcollective.microservice.persistence.entity.Video;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public String processVideoAndUpload(List<VideoFormat> videoFormats, Video video, String uploadPath, MultipartFile videoFile) {
        Slugify slug = new Slugify();

        String originalFileName = videoFile.getOriginalFilename();
        String fileNameNoExtension = slug.slugify(FilenameUtils.getBaseName(originalFileName));

        for(VideoFormat videoFormat : videoFormats) {
            log.debug("Uploading video file [{}]", originalFileName);

            String updatedFileNameNoExtension = videoFormat.getFileName() + "_" + fileNameNoExtension;
            File tmpFile = convertVideo(video, updatedFileNameNoExtension, videoFile, videoFormat);

            amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

            tmpFile.delete();

            if(video.isFeatured() && video.getVideoPreviewStartTime() != null && video.getVideoPreviewEndTime() != null) {
                log.debug("Creating video preview file from [{}] to [{}]", video.getVideoPreviewStartTime(), video.getVideoPreviewStartTime());

                String updatedPreviewFileNameNoExtension = videoFormat.getFileName() + "_" + fileNameNoExtension + "_preview";
                tmpFile = createVideoPreview(video, updatedPreviewFileNameNoExtension, videoFile, videoFormat, video.getVideoPreviewStartTime(), video.getVideoPreviewEndTime());

                amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

                tmpFile.delete();
            }
        }

        // Get video thumbnail..
        log.debug("Uploading video thumbnail");

        File tmpFile = getVideoThumbnail(video, fileNameNoExtension, videoFile);

        amazonS3Service.upload(tmpFile, uploadPath, tmpFile.getName());

        return fileNameNoExtension;
    }

    private File getVideoThumbnail(Video video, String fileNameNoExtension, MultipartFile videoFile) {
        try {
            String originalFileName = videoFile.getOriginalFilename();

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

            return outputFile;
        } catch(IOException e) {

        }

        return null;
    }

    private File convertVideo(Video video, String fileNameNoExtension, MultipartFile videoFile, VideoFormat videoFormat) {
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
                    .setFormat(videoFormat.getFormat())        // Format is inferred from filename, or can be set
//                    .setTargetSize(250_000)  // Aim for a 250KB file
                    .setVideoQuality(1.0)

                    .disableSubtitle()       // No subtiles

                    .setVideoCodec(videoFormat.getCodec())     // Video using x264

                    .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL) // Allow FFmpeg to use experimental specs
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

        FileOutputStream fos = new FileOutputStream(tmpAudioFile);

        fos.write(videoFile.getBytes());

        fos.close();

        return tmpAudioFile;
    }
}
