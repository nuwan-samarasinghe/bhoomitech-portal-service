package com.bhoomitech.portalservice.apidocs.project;

import com.bhoomitech.portalservice.model.ProjectFileType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectFileInfoDocument {
    private Long projectInfoId;
    @NotBlank(message = "project file type is a mandatory field")
    @NotEmpty(message = "project file type is a mandatory field")
    private ProjectFileType projectFileType;
    @NotBlank(message = "base point id is a mandatory field")
    @NotEmpty(message = "base point id is a mandatory field")
    private String basePointId;
    @NotBlank(message = "file name is a mandatory field")
    @NotEmpty(message = "file name is a mandatory field")
    private List<String> fileNames = new ArrayList<>();
    @NotBlank(message = "file location is a mandatory field")
    @NotEmpty(message = "file location is a mandatory field")
    private List<String> fileLocations = new ArrayList<>();
    @NotBlank(message = "antenna height is a mandatory field")
    @NotEmpty(message = "antenna height is a mandatory field")
    private String antennaHeight;
    @NotBlank(message = "antenna brand is a mandatory field")
    @NotEmpty(message = "antenna brand is a mandatory field")
    private String antennaBrand;
    @NotBlank(message = "antenna model is a mandatory field")
    @NotEmpty(message = "antenna model is a mandatory field")
    private String antennaModel;
    private String gpsCoordinatesLat;
    private String gpsCoordinatesLon;
    private String gpsCoordinatesZ;
    private String message;
}
