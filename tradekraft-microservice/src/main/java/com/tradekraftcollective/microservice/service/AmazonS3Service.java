package com.tradekraftcollective.microservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.extern.slf4j.Slf4j;
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

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by brandonfeist on 9/7/17.
 */
@Slf4j
@Service
public class AmazonS3Service {

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

    private PutObjectResult upload(File file, String uploadKey) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, uploadKey, file);

        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult putObjectResult = amazonS3Client.putObject(putObjectRequest);

        return putObjectResult;
    }

    public List<PutObjectResult> upload(MultipartFile[] multipartFiles, String filePath) {
        List<PutObjectResult> putObjectResults = new ArrayList<>();

        Arrays.stream(multipartFiles)
                .filter(multipartFile -> !StringUtils.isEmpty(multipartFile.getOriginalFilename()))
                .forEach(multipartFile -> {
                    String uploadKey = (filePath + multipartFile.getOriginalFilename());

                    try {
                        putObjectResults.add(upload(multipartFile.getInputStream(), uploadKey));

                        log.info("Successfully uploaded file https://s3.amazonaws.com/{}/{}", bucket, uploadKey);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });

        return putObjectResults;
    }

    public PutObjectResult upload(MultipartFile multipartFile, String filePath, String fileName) {
        log.info("Uploading file: {}", multipartFile.getOriginalFilename());

        PutObjectResult putObjectResult = null;
        String uploadKey = (filePath + fileName);

        try {
            putObjectResult = upload(multipartFile.getInputStream(), uploadKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Successfully uploaded file https://s3.amazonaws.com/{}/{}", bucket, uploadKey);
        return putObjectResult;
    }

    public PutObjectResult upload(File file, String filePath, String fileName) {
        log.info("Uploading file: {}", file.getName());

        PutObjectResult putObjectResult = null;
        String uploadKey = (filePath + fileName);

        putObjectResult = upload(file, uploadKey);

        log.info("Successfully uploaded file https://s3.amazonaws.com/{}/{}", bucket, uploadKey);
        return putObjectResult;
    }

    public void moveObject(String originalyKey, String newKey) {
        log.info("Moving AWS Key: {} to new Key: {}", originalyKey, newKey);

        amazonS3Client.copyObject(bucket, originalyKey, bucket, newKey);

        delete(originalyKey);
    }

    public void delete(String deleteKey) {
        log.info("Deleting AWS Key: {}", deleteKey);

        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, deleteKey);

        amazonS3Client.deleteObject(deleteObjectRequest);
    }

    public ObjectListing getDirectoryContent(String prefix, String delimiter) {
        log.info("Getting AWS directory objects with Prefix: {} and Delimiter: {}", prefix, delimiter);

        ListObjectsRequest listObjectsRequest = null;

        if(delimiter != null) {
            listObjectsRequest = new ListObjectsRequest().withBucketName(bucket)
                    .withPrefix(prefix)
                    .withDelimiter(delimiter);
        } else {
            listObjectsRequest = new ListObjectsRequest().withBucketName(bucket)
                    .withPrefix(prefix);
        }

        return amazonS3Client.listObjects(listObjectsRequest);
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
