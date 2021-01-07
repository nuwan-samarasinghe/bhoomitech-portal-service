package com.bhoomitech.portalservice.util;

import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoRequestDocument;
import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoResponseDocument;
import com.bhoomitech.portalservice.model.Project;
import com.bhoomitech.portalservice.model.ProjectFileInfo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.function.Function;

public final class ProjectConverter {
    public static Function<ProjectFileInfo, ProjectFileInfoResponseDocument> fileInfoResponseDocumentFunction = projectFileInfo -> {
        ProjectFileInfoResponseDocument infoResponseDocument = new ProjectFileInfoResponseDocument();
        infoResponseDocument.setProjectInfoId(projectFileInfo.getId());
        infoResponseDocument.setProjectFileType(projectFileInfo.getProjectFileType());
        infoResponseDocument.setBasePointId(projectFileInfo.getBasePointId());
        infoResponseDocument.setFileName(projectFileInfo.getFileName());
        infoResponseDocument.setFileLocation(projectFileInfo.getFileLocation());
        infoResponseDocument.setAntennaHeight(projectFileInfo.getAntennaHeight());
        infoResponseDocument.setAntennaBrand(projectFileInfo.getAntennaBrand());
        infoResponseDocument.setAntennaModel(projectFileInfo.getAntennaModel());
        infoResponseDocument.setGpsCoordinatesLat(projectFileInfo.getGpsCoordinatesLat());
        infoResponseDocument.setGpsCoordinatesLon(projectFileInfo.getGpsCoordinatesLon());
        infoResponseDocument.setGpsCoordinatesZ(projectFileInfo.getGpsCoordinatesZ());
        infoResponseDocument.setProjectId(projectFileInfo.getId());
        infoResponseDocument.setProjectName(projectFileInfo.getFileName());
        infoResponseDocument.setProjectStartTimestamp(projectFileInfo.getProject().getProjectStartTimestamp().toString());
        infoResponseDocument.setProjectEndTimestamp(projectFileInfo.getProject().getProjectEndTimestamp().toString());
        infoResponseDocument.setProjectId(projectFileInfo.getProject().getId());
        infoResponseDocument.setUserHref(projectFileInfo.getProject().getUserHref());
        infoResponseDocument.setAgreementStatus(projectFileInfo.getProject().getAgreementStatus());
        infoResponseDocument.setPrice(projectFileInfo.getProject().getPrice());
        infoResponseDocument.setCreatedTimestamp(projectFileInfo.getProject().getCreatedTimestamp().toString());
        return infoResponseDocument;
    };

    public static Function<ProjectFileInfoRequestDocument, ProjectFileInfo> documentProjectFileInfoFunction = projectFileInfoRequestDocument -> {
        Project project = new Project();
        project.setId(projectFileInfoRequestDocument.getProjectId());
        project.setProjectName(projectFileInfoRequestDocument.getProjectName());
        project.setProjectStartTimestamp(Timestamp.valueOf(projectFileInfoRequestDocument.getProjectStartTimestamp()));
        project.setProjectEndTimestamp(Timestamp.valueOf(projectFileInfoRequestDocument.getProjectEndTimestamp()));
        project.setUserHref(projectFileInfoRequestDocument.getUserHref());
        project.setAgreementStatus(projectFileInfoRequestDocument.getAgreementStatus());
        project.setPrice(projectFileInfoRequestDocument.getPrice());
        project.setCreatedTimestamp(Timestamp.from(new Date().toInstant()));

        ProjectFileInfo projectFileInfo = new ProjectFileInfo();
        projectFileInfo.setId(projectFileInfoRequestDocument.getProjectId());
        projectFileInfo.setProjectFileType(projectFileInfoRequestDocument.getProjectFileType());
        projectFileInfo.setBasePointId(projectFileInfoRequestDocument.getBasePointId());
        projectFileInfo.setFileName(projectFileInfoRequestDocument.getFileName());
        projectFileInfo.setFileLocation(projectFileInfoRequestDocument.getFileLocation());
        projectFileInfo.setAntennaHeight(projectFileInfoRequestDocument.getAntennaHeight());
        projectFileInfo.setAntennaBrand(projectFileInfoRequestDocument.getAntennaBrand());
        projectFileInfo.setAntennaModel(projectFileInfoRequestDocument.getAntennaModel());
        projectFileInfo.setGpsCoordinatesLat(projectFileInfoRequestDocument.getGpsCoordinatesLat());
        projectFileInfo.setGpsCoordinatesLon(projectFileInfoRequestDocument.getGpsCoordinatesLon());
        projectFileInfo.setGpsCoordinatesZ(projectFileInfoRequestDocument.getGpsCoordinatesZ());
        projectFileInfo.setProject(project);

        return projectFileInfo;
    };
}
