package com.tradekraftcollective.microservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by brandonfeist on 9/7/17.
 */
@Service
public class AmazonS3Service {
    private static final Logger logger = LoggerFactory.getLogger(AmazonS3Service.class);

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${vcap.services.amazon-aws.credentials.bucket}")
    private String bucket;

    private PutObjectResult upload(String filePath, String uploadKey) throws FileNotFoundException {
        return upload(new FileInputStream(filePath), uploadKey);
    }

    private PutObjectResult upload(InputStream inputStream, String uploadKey) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, inputStream, new ObjectMetadata());

        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

        IOUtils.closeQuietly(inputStream);

        return putObjectResult;
    }

    public List<PutObjectResult> upload(MultipartFile[] multipartFiles, String filePath) {
        List<PutObjectResult> putObjectResults = new ArrayList<>();
        //Randomize file name to random uuid string to avoid duplicates

        Arrays.stream(multipartFiles)
                .filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
                .forEach(multipartFile -> {
                    String uploadKey = (filePath + multipartFile.getOriginalFilename());

                    try {
                        putObjectResults.add(upload(multipartFile.getInputStream(), uploadKey));

                        logger.info("Successfully uploaded file https://s3.amazonaws.com/{}/{}", bucket, uploadKey);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });

        return putObjectResults;
    }

    public PutObjectResult upload(MultipartFile multipartFile, String filePath) {
        logger.info("Uploading file {}", multipartFile.getOriginalFilename());
        //Randomize file name to random uuid string to avoid duplicates

        PutObjectResult putObjectResult = null;
        String uploadKey = (filePath + multipartFile.getOriginalFilename());

        try {
            putObjectResult = upload(multipartFile.getInputStream(), uploadKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Successfully uploaded file https://s3.amazonaws.com/{}/{}", bucket, uploadKey);
        return putObjectResult;
    }

    public ResponseEntity<byte[]> download(String key) throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);

        S3Object s3Object = amazonS3Client.getObject(getObjectRequest);

        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(s3ObjectInputStream);

        String fileName = URLEncoder.encode(key, "UTF-8").replaceAll("\\+", "%20");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(bytes.length);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
    }

    public List<S3ObjectSummary> list() {
        ObjectListing objectListing = amazonS3Client.listObjects(new ListObjectsRequest().withBucketName(bucket));

        List<S3ObjectSummary> s3ObjectSummaries = objectListing.getObjectSummaries();

        return s3ObjectSummaries;
    }
}
