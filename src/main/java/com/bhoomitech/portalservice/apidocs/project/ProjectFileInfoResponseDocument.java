package com.bhoomitech.portalservice.apidocs.project;

import com.bhoomitech.portalservice.model.ProjectFileType;
import lombok.Data;

@Data
public class ProjectFileInfoResponseDocument {
    private Long projectInfoId;
    private ProjectFileType projectFileType;
    private String basePointId;
    private String fileName;
    private String fileLocation;
    private Long antennaHeight;
    private String antennaBrand;
    private String antennaModel;
    private String gpsCoordinatesLat;
    private String gpsCoordinatesLon;
    private String gpsCoordinatesZ;
    private Long projectId;
    private String projectName;
    private String projectStartTimestamp;
    private String projectEndTimestamp;
    private String userHref;
    private Boolean agreementStatus;
    private Long price;
    private String createdTimestamp;
}
