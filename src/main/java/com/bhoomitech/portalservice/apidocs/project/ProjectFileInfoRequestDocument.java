package com.bhoomitech.portalservice.apidocs.project;

import com.bhoomitech.portalservice.model.ProjectFileType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class ProjectFileInfoRequestDocument {

    private Long projectInfoId;
    @NotBlank(message = "project file type is a mandatory field")
    @NotEmpty(message = "project file type is a mandatory field")
    private ProjectFileType projectFileType;
    @NotBlank(message = "base point id is a mandatory field")
    @NotEmpty(message = "base point id is a mandatory field")
    private String basePointId;
    @NotBlank(message = "file name is a mandatory field")
    @NotEmpty(message = "file name is a mandatory field")
    private String fileName;
    @NotBlank(message = "file location is a mandatory field")
    @NotEmpty(message = "file location is a mandatory field")
    private String fileLocation;
    @NotBlank(message = "antenna height is a mandatory field")
    @NotEmpty(message = "antenna height is a mandatory field")
    private Long antennaHeight;
    @NotBlank(message = "antenna brand is a mandatory field")
    @NotEmpty(message = "antenna brand is a mandatory field")
    private String antennaBrand;
    @NotBlank(message = "antenna model is a mandatory field")
    @NotEmpty(message = "antenna model is a mandatory field")
    private String antennaModel;
    private String gpsCoordinatesLat;
    private String gpsCoordinatesLon;
    private String gpsCoordinatesZ;
    private Long projectId;
    @NotBlank(message = "project name is a mandatory field")
    @NotEmpty(message = "project name is a mandatory field")
    private String projectName;
    @NotBlank(message = "project start date time is a mandatory field")
    @NotEmpty(message = "project start date time is a mandatory field")
    private String projectStartTimestamp;
    @NotBlank(message = "project end date time is a mandatory field")
    @NotEmpty(message = "project end date time is a mandatory field")
    private String projectEndTimestamp;
    @NotBlank(message = "user href is a mandatory field")
    @NotEmpty(message = "user href is a mandatory field")
    private String userHref;
    @NotBlank(message = "agreement status is a mandatory field")
    @NotEmpty(message = "agreement status is a mandatory field")
    private Boolean agreementStatus;
    @NotBlank(message = "price is a mandatory field")
    @NotEmpty(message = "price is a mandatory field")
    private Long price;
}
