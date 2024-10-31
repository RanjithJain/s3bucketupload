package com.example.awss3.controller;

import com.example.awss3.service.S3DirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.awss3.service.S3Service;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@RestController
public class FileUploaderController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private S3DirService s3DirService;



    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        System.out.println("Hello AWS User");
        return(new ResponseEntity<>("Success", HttpStatus.resolve(200)));

    }



    @PostMapping("/uploadFile/{bucketName}")
    public ResponseEntity<String> uploadFile(@PathVariable String bucketName,
                                             @RequestParam("file") MultipartFile multipartFile) throws IOException {
        // Convert MultipartFile to File
        File file = convertMultiPartToFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();

        s3Service.uploadFile(bucketName, fileName, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PostMapping("/createBucket/{bucketName}")
    public ResponseEntity<String> createBucket(@PathVariable String bucketName) {
        s3Service.createBucket(bucketName);
        return ResponseEntity.ok("Bucket created successfully");
    }

    @PostMapping("/upload-file")
    public void uploadFile(@RequestParam String bucketName,
                           @RequestParam String filePath,
                           @RequestParam String directoryName) {
        s3DirService.uploadFileToDirectory(bucketName, Paths.get(filePath), directoryName);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
