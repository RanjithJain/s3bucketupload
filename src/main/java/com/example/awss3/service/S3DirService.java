package com.example.awss3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


import java.nio.file.Path;

@Service
public class S3DirService {

    @Autowired
    private S3Client s3Client;

    public void uploadFileToDirectory( String bucketName, Path filePath, String directoryName) {
        try {

            PutObjectRequest putObj = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(directoryName)
                    .build();
            s3Client.putObject(putObj, RequestBody.fromFile(filePath));
            System.out.println("Successfully placed " + directoryName +" into bucket "+bucketName);

        }

        catch (S3Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }


    }


}
