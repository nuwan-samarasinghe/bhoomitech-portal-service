package com.bhoomitech.portalservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bhoomitech.portalservice.model.FileStatus;
import com.bhoomitech.portalservice.common.PortalServiceException;
import com.xcodel.commons.project.ProjectFileType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

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

    public FileStatus fileUpload(
            String additionalDirStructure,
            @NonNull MultipartFile[] file,
            @NonNull String projectName,
            @NonNull ProjectFileType projectFileType) {
        FileStatus fileStatus = new FileStatus();
        fileStatus.setProjectFileType(projectFileType);
        validateFilesBeforeUpload(file, fileStatus);
        if (Objects.isNull(fileStatus.getErrorMessage())) {
            for (MultipartFile multipartFile : file) {
                uploadFile(additionalDirStructure, projectName, multipartFile, fileStatus, projectFileType);
            }
        }
        return fileStatus;
    }

    private void validateFilesBeforeUpload(MultipartFile[] files, FileStatus fileStatus) {
        if (!Objects.nonNull(files)) {
            fileStatus.setErrorMessage("please provide the company name");
        }
        if (files.length > maxFileUploadLimit) {
            fileStatus.setErrorMessage("exceeded the max file upload count");
        }
        if (files.length < minFileUploadLimit) {
            fileStatus.setErrorMessage("need at least single file provided for upload");
        }
        checkFileExt(files, fileStatus);
    }

    private void checkFileExt(MultipartFile[] knownFiles, FileStatus fileStatus) {
        boolean isValidFileAvailable = false;
        for (MultipartFile file : knownFiles) {
            String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
            if (Pattern.compile("\\d+[a-z]").matcher(fileExt).matches()) {
                isValidFileAvailable = true;
            }
            break;
        }
        if (!isValidFileAvailable) {
            fileStatus.setErrorMessage("at least there should be a file ext with Ex.20n");
        }
    }

    private void uploadFile(
            String additionalDirStructure,
            @NonNull String projectName,
            @NonNull MultipartFile multipartFile,
            @NonNull FileStatus fileStatus,
            @NonNull ProjectFileType projectFileType) {
        projectName = projectName.replaceAll(" ", "_");
        log.info("project name for the s3 {}", projectName);
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            String location = "";
            if (projectFileType == ProjectFileType.UNKNOWN_FILE) {
                location = (additionalDirStructure != null ? additionalDirStructure : "") + projectName + UNKNOWN + multipartFile.getOriginalFilename();
                log.info("generating unknown file location {}", location);
            } else if (projectFileType == ProjectFileType.KNOWN_FILE) {
                location = (additionalDirStructure != null ? additionalDirStructure : "") + projectName + KNOWN + multipartFile.getOriginalFilename();
                log.info("generating known file location {}", location);
            }
            amazonS3Client.putObject(new PutObjectRequest(bucketName, location, file));
            log.info("upload success file name {}", multipartFile.getOriginalFilename());
            fileStatus.getFileNames().add(multipartFile.getOriginalFilename());
            fileStatus.getFileLocations().add(fileBaseLocation + location);
        } catch (IOException e) {
            log.error("error [" + e.getMessage() + "] occurred while uploading [" + multipartFile.getOriginalFilename() + "] ");
            fileStatus.setErrorMessage(projectFileType + " upload error file name" + multipartFile.getOriginalFilename());
            throw new PortalServiceException("error [" + e.getMessage() + "] occurred while uploading [" + multipartFile.getOriginalFilename() + "] ", e);
        }
    }
}
