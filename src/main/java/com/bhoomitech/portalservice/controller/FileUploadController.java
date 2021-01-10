package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.apidocs.project.FileUploadStatusDocument;
import com.bhoomitech.portalservice.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/upload")
    public FileUploadStatusDocument uploadFileToS3Bucket(
            @RequestParam("knownFiles") MultipartFile[] knownFiles,
            @RequestParam("unKnownFiles") MultipartFile[] unKnownFiles,
            @RequestParam("companyName") String companyName
    ) {
        return fileUploadService.fileUpload(knownFiles, unKnownFiles, companyName);
    }

}
