package com.bhoomitech.portalservice.apidocs.project;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UploadMultiPartFiles {
    private List<MultipartFile> multipartFiles;
}
