package com.bhoomitech.portalservice.apidocs.project;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileUploadStatusDocument {
    private List<FileStatusDocument> statusUnknownUpload = new ArrayList<>();
    private List<FileStatusDocument> statusKnownUpload = new ArrayList<>();
    private String validationMessage;
}
