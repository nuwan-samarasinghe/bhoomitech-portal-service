package com.bhoomitech.portalservice.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bhoomitech.portalservice.apidocs.project.FileStatusDocument;
import com.bhoomitech.portalservice.apidocs.project.FileUploadStatusDocument;
import com.bhoomitech.portalservice.apidocs.project.UploadMultiPartFiles;
import com.bhoomitech.portalservice.common.PortalServiceException;
import com.bhoomitech.portalservice.model.ProjectFileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    public FileUploadStatusDocument uploadFileToS3Bucket(
            @RequestParam("knownFiles") MultipartFile[] knownFiles,
            @RequestParam("unKnownFiles") MultipartFile[] unKnownFiles,
            @RequestParam("companyName") String companyName
    ) {
        FileUploadStatusDocument fileUploadStatusDocument = new FileUploadStatusDocument();
        for (MultipartFile multipartFile : knownFiles) {
            uploadFile(companyName, multipartFile, fileUploadStatusDocument, ProjectFileType.UNKNOWN_FILE);
        }
        for (MultipartFile multipartFile : unKnownFiles) {
            uploadFile(companyName, multipartFile, fileUploadStatusDocument, ProjectFileType.KNOWN_FILE);
        }
        return fileUploadStatusDocument;
    }

    private void uploadFile(
            String companyName,
            MultipartFile multipartFile,
            FileUploadStatusDocument fileUploadStatusDocument,
            ProjectFileType projectFileType) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            String location = "";
            if (projectFileType == ProjectFileType.UNKNOWN_FILE) {
                location = companyName + "/unknown/" + multipartFile.getOriginalFilename();
            } else if (projectFileType == ProjectFileType.KNOWN_FILE) {
                location = companyName + "/known/" + multipartFile.getOriginalFilename();
            }
            amazonS3Client.putObject(new PutObjectRequest(BHOOMI_TECH_S_3_BUCKET, location, file));
            log.info("upload success file name {}", multipartFile.getOriginalFilename());
            createStatusDocument(fileUploadStatusDocument, multipartFile, projectFileType, "success");
        } catch (IOException e) {
            log.error("error [" + e.getMessage() + "] occurred while uploading [" + multipartFile.getOriginalFilename() + "] ");
            createStatusDocument(fileUploadStatusDocument, multipartFile, projectFileType, "error");
            throw new PortalServiceException("error [" + e.getMessage() + "] occurred while uploading [" + multipartFile.getOriginalFilename() + "] ", e);
        }
    }

    private void createStatusDocument(FileUploadStatusDocument fileUploadStatusDocument, MultipartFile multipartFile, ProjectFileType projectFileType, String status) {
        if (projectFileType == ProjectFileType.UNKNOWN_FILE) {
            FileStatusDocument fileStatusDocument = new FileStatusDocument();
            fileStatusDocument.setStatusUpload(Boolean.TRUE);
            fileStatusDocument.setMessage(projectFileType + "upload " + status + " file name" + multipartFile.getOriginalFilename());
            fileUploadStatusDocument.getStatusUnknownUpload().add(fileStatusDocument);
        } else if (projectFileType == ProjectFileType.KNOWN_FILE) {
            FileStatusDocument fileStatusDocument = new FileStatusDocument();
            fileStatusDocument.setStatusUpload(Boolean.TRUE);
            fileStatusDocument.setMessage(projectFileType + "upload " + status + " file name" + multipartFile.getOriginalFilename());
            fileUploadStatusDocument.getStatusKnownUpload().add(fileStatusDocument);
        }
    }

}
