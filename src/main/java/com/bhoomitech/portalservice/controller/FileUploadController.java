package com.bhoomitech.portalservice.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {

    private final AmazonS3 amazonS3Client;

    public FileUploadController(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
        File file = new File("D:\\PROJECTS\\boomitech\\portal-service\\src\\main\\java\\com\\bhoomitech\\portalservice\\controller\\test-file.txt");
        amazonS3Client.putObject(new PutObjectRequest("bhoomi-tech-s3-bucket", "test-file-upload", file));
    }


}
