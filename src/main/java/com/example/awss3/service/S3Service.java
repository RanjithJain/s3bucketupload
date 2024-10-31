package com.example.awss3.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.S3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import java.io.File;
import java.nio.file.Path;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    public S3Service(@Value("${aws.accessKeyId}") String accessKeyId,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.region}") String region) {

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
    }

    public void createBucket(String bucketName) {
        if (!s3Client.doesBucketExistV2(bucketName)) {
            s3Client.createBucket(new CreateBucketRequest(bucketName));
            System.out.println("Bucket created successfully.");
        } else {
            System.out.println("Bucket already exists.");
        }
    }

    public void uploadFile(String bucketName, String key, File file) {
       s3Client.putObject(new PutObjectRequest(bucketName, key, file));
    }




}
