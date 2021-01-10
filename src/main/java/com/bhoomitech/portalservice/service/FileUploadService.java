package com.bhoomitech.portalservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bhoomitech.portalservice.apidocs.project.FileStatusDocument;
import com.bhoomitech.portalservice.apidocs.project.FileUploadStatusDocument;
import com.bhoomitech.portalservice.common.PortalServiceException;
import com.bhoomitech.portalservice.model.ProjectFileType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class FileUploadService {

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    public static final String UNKNOWN = "/unknown/";
    public static final String KNOWN = "/known/";
    private final AmazonS3 amazonS3Client;

    @Value("${app.custom-configs.max-file-upload-limit}")
    private Integer maxFileUploadLimit;

    @Value("${app.custom-configs.min-file-upload-limit}")
    private Integer minFileUploadLimit;

    @Value("${app.custom-configs.bucket-name}")
    private String bucketName;

    @Value("${app.custom-configs.file-base-location}")
    private String fileBaseLocation;

    public FileUploadService(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    public FileUploadStatusDocument fileUpload(MultipartFile[] knownFiles, MultipartFile[] unKnownFiles, String companyName) {
        FileUploadStatusDocument fileUploadStatusDocument = new FileUploadStatusDocument();
        if (validateFilesBeforeUpload(knownFiles, unKnownFiles, companyName, fileUploadStatusDocument)) {
            for (MultipartFile multipartFile : knownFiles) {
                uploadFile(companyName, multipartFile, fileUploadStatusDocument, ProjectFileType.UNKNOWN_FILE);
            }
            for (MultipartFile multipartFile : unKnownFiles) {
                uploadFile(companyName, multipartFile, fileUploadStatusDocument, ProjectFileType.KNOWN_FILE);
            }
        }
        return fileUploadStatusDocument;
    }

    private boolean validateFilesBeforeUpload(
            MultipartFile[] knownFiles,
            MultipartFile[] unKnownFiles,
            String companyName,
            FileUploadStatusDocument fileUploadStatusDocument) {
        if (!Objects.nonNull(companyName)) {
            fileUploadStatusDocument.setValidationMessage("No company name provided");
            return false;
        }
        if (!Objects.nonNull(knownFiles)) {
            fileUploadStatusDocument.setValidationMessage("empty known files provided");
            return false;
        }
        if (!Objects.nonNull(unKnownFiles)) {
            fileUploadStatusDocument.setValidationMessage("empty un-known files provided");
            return false;
        }
        if (knownFiles.length > maxFileUploadLimit) {
            fileUploadStatusDocument.setValidationMessage("more than 5 files provided for upload");
            return false;
        }
        if (knownFiles.length < minFileUploadLimit) {
            fileUploadStatusDocument.setValidationMessage("need at least single file provided for upload");
            return false;
        }
        if (unKnownFiles.length > maxFileUploadLimit) {
            fileUploadStatusDocument.setValidationMessage("more than 5 files provided for upload");
            return false;
        }
        if (unKnownFiles.length < minFileUploadLimit) {
            fileUploadStatusDocument.setValidationMessage("need at least single file provided for upload");
            return false;
        }
        fileUploadStatusDocument.setValidationMessage("validation success full");
        return true;
    }

    private void uploadFile(
            @NonNull String companyName,
            @NonNull MultipartFile multipartFile,
            @NonNull FileUploadStatusDocument fileUploadStatusDocument,
            @NonNull ProjectFileType projectFileType) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            String location = "";
            if (projectFileType == ProjectFileType.UNKNOWN_FILE) {
                location = companyName + UNKNOWN + multipartFile.getOriginalFilename();
            } else if (projectFileType == ProjectFileType.KNOWN_FILE) {
                location = companyName + KNOWN + multipartFile.getOriginalFilename();
            }
            amazonS3Client.putObject(new PutObjectRequest(bucketName, location, file));
            log.info("upload success file name {}", multipartFile.getOriginalFilename());
            createStatusDocument(fileUploadStatusDocument, multipartFile, projectFileType, SUCCESS, location);
        } catch (IOException e) {
            log.error("error [" + e.getMessage() + "] occurred while uploading [" + multipartFile.getOriginalFilename() + "] ");
            createStatusDocument(fileUploadStatusDocument, multipartFile, projectFileType, ERROR, null);
            throw new PortalServiceException("error [" + e.getMessage() + "] occurred while uploading [" + multipartFile.getOriginalFilename() + "] ", e);
        }
    }

    private void createStatusDocument(
            @NonNull FileUploadStatusDocument fileUploadStatusDocument,
            @NonNull MultipartFile multipartFile,
            @NonNull ProjectFileType projectFileType,
            @NonNull String status,
            @NonNull String location) {
        if (projectFileType == ProjectFileType.UNKNOWN_FILE) {
            FileStatusDocument fileStatusDocument = new FileStatusDocument();
            fileStatusDocument.setStatusUpload(Boolean.TRUE);
            fileStatusDocument.setMessage(projectFileType + " upload " + status + " file name" + multipartFile.getOriginalFilename());
            if (status.equals(SUCCESS)) {
                fileStatusDocument.setFileLocation(fileBaseLocation + location);
            }
            fileUploadStatusDocument.getStatusUnknownUpload().add(fileStatusDocument);
        } else if (projectFileType == ProjectFileType.KNOWN_FILE) {
            FileStatusDocument fileStatusDocument = new FileStatusDocument();
            fileStatusDocument.setStatusUpload(Boolean.TRUE);
            fileStatusDocument.setMessage(projectFileType + " upload " + status + " file name" + multipartFile.getOriginalFilename());
            if (status.equals(SUCCESS)) {
                fileStatusDocument.setFileLocation(fileBaseLocation + location);
            }
            fileUploadStatusDocument.getStatusKnownUpload().add(fileStatusDocument);
        }
    }
}
