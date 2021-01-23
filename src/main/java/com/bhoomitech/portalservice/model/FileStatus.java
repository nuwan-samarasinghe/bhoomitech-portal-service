package com.bhoomitech.portalservice.model;

import com.xcodel.commons.project.ProjectFileType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FileStatus {
    private ProjectFileType projectFileType;
    private List<String> fileLocations = new ArrayList<>();
    private List<String> fileNames = new ArrayList<>();
    private String errorMessage;
}
