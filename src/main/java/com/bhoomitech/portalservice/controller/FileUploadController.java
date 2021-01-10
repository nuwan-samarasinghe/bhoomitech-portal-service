package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.apidocs.project.FileUploadStatusDocument;
import com.bhoomitech.portalservice.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class FileUploadController {
    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/file")
    public FileUploadStatusDocument uploadFileToS3Bucket(
            @RequestParam("knownFiles") MultipartFile[] knownFiles,
            @RequestParam("unKnownFiles") MultipartFile[] unKnownFiles,
            @RequestParam("companyName") String companyName
    ) {
        return fileUploadService.fileUpload(knownFiles, unKnownFiles, companyName);
    }

}
