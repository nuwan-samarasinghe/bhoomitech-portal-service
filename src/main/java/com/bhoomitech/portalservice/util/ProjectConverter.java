package com.bhoomitech.portalservice.util;

import com.bhoomitech.portalservice.apidocs.project.ProjectDocument;
import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoDocument;
import com.bhoomitech.portalservice.model.Project;
import com.bhoomitech.portalservice.model.ProjectFileInfo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

public final class ProjectConverter {
    public static Function<Project, ProjectDocument> projectProjectDocumentFunction = project -> {
        ProjectDocument projectDocument = new ProjectDocument();
        projectDocument.setProjectName(project.getProjectName());
        projectDocument.setProjectStartTimestamp(project.getProjectStartTimestamp().toString());
        projectDocument.setProjectEndTimestamp(project.getProjectEndTimestamp().toString());
        projectDocument.setProjectId(project.getId());
        projectDocument.setUserHref(project.getUserHref());
        projectDocument.setAgreementStatus(project.getAgreementStatus());
        projectDocument.setPrice(project.getPrice());
        projectDocument.setCreatedTimestamp(project.getCreatedTimestamp().toString());
        project.getFileInfos().forEach(projectFileInfo -> {
            ProjectFileInfoDocument projectFileInfoDocument = new ProjectFileInfoDocument();
            projectFileInfoDocument.setProjectInfoId(projectFileInfo.getId());
            projectFileInfoDocument.setProjectFileType(projectFileInfo.getProjectFileType());
            projectFileInfoDocument.setBasePointId(projectFileInfo.getBasePointId());
            projectFileInfoDocument.setFileName(projectFileInfo.getFileName());
            projectFileInfoDocument.setFileLocation(projectFileInfo.getFileLocation());
            projectFileInfoDocument.setAntennaHeight(projectFileInfo.getAntennaHeight());
            projectFileInfoDocument.setAntennaBrand(projectFileInfo.getAntennaBrand());
            projectFileInfoDocument.setAntennaModel(projectFileInfo.getAntennaModel());
            projectFileInfoDocument.setGpsCoordinatesLat(projectFileInfo.getGpsCoordinatesLat());
            projectFileInfoDocument.setGpsCoordinatesLon(projectFileInfo.getGpsCoordinatesLon());
            projectFileInfoDocument.setGpsCoordinatesZ(projectFileInfo.getGpsCoordinatesZ());
            projectDocument.getProjectFileInfoDocuments().add(projectFileInfoDocument);
        });
        return projectDocument;
    };

    public static Function<ProjectDocument, Project> projectDocumentProjectFunction = projectDocument -> {
        Project project = new Project();
        project.setId(projectDocument.getProjectId());
        project.setProjectName(projectDocument.getProjectName());
        project.setProjectStartTimestamp(Timestamp.valueOf(projectDocument.getProjectStartTimestamp()));
        project.setProjectEndTimestamp(Timestamp.valueOf(projectDocument.getProjectEndTimestamp()));
        project.setUserHref(projectDocument.getUserHref());
        project.setAgreementStatus(projectDocument.getAgreementStatus());
        project.setPrice(projectDocument.getPrice());
        project.setCreatedTimestamp(Timestamp.from(new Date().toInstant()));
        project.setFileInfos(new ArrayList<>());
        projectDocument.getProjectFileInfoDocuments().forEach(projectFileInfoDocument -> {
            ProjectFileInfo projectFileInfo = new ProjectFileInfo();
            projectFileInfo.setId(projectFileInfoDocument.getProjectInfoId());
            projectFileInfo.setProjectFileType(projectFileInfoDocument.getProjectFileType());
            projectFileInfo.setBasePointId(projectFileInfoDocument.getBasePointId());
            projectFileInfo.setFileName(projectFileInfoDocument.getFileName());
            projectFileInfo.setFileLocation(projectFileInfoDocument.getFileLocation());
            projectFileInfo.setAntennaHeight(projectFileInfoDocument.getAntennaHeight());
            projectFileInfo.setAntennaBrand(projectFileInfoDocument.getAntennaBrand());
            projectFileInfo.setAntennaModel(projectFileInfoDocument.getAntennaModel());
            projectFileInfo.setGpsCoordinatesLat(projectFileInfoDocument.getGpsCoordinatesLat());
            projectFileInfo.setGpsCoordinatesLon(projectFileInfoDocument.getGpsCoordinatesLon());
            projectFileInfo.setGpsCoordinatesZ(projectFileInfoDocument.getGpsCoordinatesZ());
            project.getFileInfos().add(projectFileInfo);
        });
        return project;
    };
}
