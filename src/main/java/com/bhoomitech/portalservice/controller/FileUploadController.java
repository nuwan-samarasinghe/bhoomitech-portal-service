package com.bhoomitech.portalservice.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bhoomitech.portalservice.apidocs.project.UploadMultiPartFiles;
import com.bhoomitech.portalservice.common.PortalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileUploadController {

    public static final String BHOOMI_TECH_S_3_BUCKET = "bhoomi-tech-s3-bucket";
    private final AmazonS3 amazonS3Client;

    public FileUploadController(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/upload")
    public Boolean uploadFileToS3Bucket(@RequestBody UploadMultiPartFiles uploadMultiPartFiles) {
        boolean isUploaded = false;
        for (MultipartFile multipartFile : uploadMultiPartFiles.getMultipartFiles()) {//creating the file in the server (temporarily)
            File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(multipartFile.getBytes());
                amazonS3Client.putObject(new PutObjectRequest(BHOOMI_TECH_S_3_BUCKET, multipartFile.getOriginalFilename(), file));
                isUploaded = true;
                log.info("upload success file name {}", multipartFile.getOriginalFilename());
            } catch (IOException e) {
                log.error("error [" + e.getMessage() + "] occurred while uploading [" + multipartFile.getOriginalFilename() + "] ");
                throw new PortalServiceException("error [" + e.getMessage() + "] occurred while uploading [" + multipartFile.getOriginalFilename() + "] ", e);
            }
        }
        return isUploaded;
    }

}
