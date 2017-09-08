package com.tradekraftcollective.microservice.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by brandonfeist on 9/7/17.
 */
@Configuration
public class AWSConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AWSConfiguration.class);

    @Value("${vcap.services.amazon-aws.credentials.access_key}")
    private String accessKey;

    @Value("${vcap.services.amazon-aws.credentials.secret_key}")
    private String secretKey;

    @Value("${vcap.services.amazon-aws.credentials.region}")
    private String region;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        logger.info("Using aws credentials");

        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentials awsCredentials) {
        logger.info("Retrieving aws client");

        AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

        return amazonS3Client;
    }
}
