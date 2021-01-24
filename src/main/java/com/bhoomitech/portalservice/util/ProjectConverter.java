package com.bhoomitech.portalservice.util;

import com.bhoomitech.portalservice.model.FileStatus;
import com.bhoomitech.portalservice.model.Project;
import com.bhoomitech.portalservice.model.ProjectFileInfo;
import com.xcodel.commons.auth.userdetail.UserDetailDocument;
import com.xcodel.commons.project.ProjectDocument;
import com.xcodel.commons.project.ProjectFileInfoDocument;

import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class ProjectConverter {

    public static BiFunction<Project, Boolean, ProjectDocument> projectProjectDocumentFunction = (project, isProjectOnly) -> {
        ProjectDocument projectDocument = new ProjectDocument();
        projectDocument.setProjectName(project.getProjectName());
        projectDocument.setProjectStartTimestamp(project.getProjectStartTimestamp().toString());
        projectDocument.setProjectEndTimestamp(project.getProjectEndTimestamp().toString());
        projectDocument.setProjectId(project.getId());
        projectDocument.setUserHref(project.getUserHref());
        projectDocument.setAgreementStatus(project.getAgreementStatus());
        projectDocument.setPrice(project.getPrice());
        projectDocument.setStatus(project.getStatus());
        projectDocument.setCreatedTimestamp(project.getCreatedTimestamp().toString());
        if (!isProjectOnly) {
            project.getFileInfos().forEach(projectFileInfo -> {
                ProjectFileInfoDocument projectFileInfoDocument = new ProjectFileInfoDocument();
                projectFileInfoDocument.setProjectInfoId(projectFileInfo.getId());
                projectFileInfoDocument.setProjectFileType(projectFileInfo.getProjectFileType());
                projectFileInfoDocument.setBasePointId(projectFileInfo.getBasePointId());
                Arrays.stream(projectFileInfo.getFileName().split(","))
                        .forEach(fileName -> projectFileInfoDocument.getFileNames().add(fileName));
                Arrays.stream(projectFileInfo.getFileLocation().split(","))
                        .forEach(fileLocation -> projectFileInfoDocument.getFileLocations().add(fileLocation));
                projectFileInfoDocument.setAntennaHeight(projectFileInfo.getAntennaHeight());
                projectFileInfoDocument.setAntennaBrand(projectFileInfo.getAntennaBrand());
                projectFileInfoDocument.setAntennaModel(projectFileInfo.getAntennaModel());
                projectFileInfoDocument.setGpsCoordinatesLat(projectFileInfo.getGpsCoordinatesLat());
                projectFileInfoDocument.setGpsCoordinatesLon(projectFileInfo.getGpsCoordinatesLon());
                projectFileInfoDocument.setGpsCoordinatesZ(projectFileInfo.getGpsCoordinatesZ());
                projectDocument.getProjectFileInfoDocuments().add(projectFileInfoDocument);
            });
        }
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
            projectFileInfo.setFileName(String.join(",", projectFileInfoDocument.getFileNames()));
            projectFileInfo.setFileLocation(String.join(",", projectFileInfoDocument.getFileLocations()));
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

    public static BiFunction<FileStatus, ProjectFileInfoDocument, ProjectFileInfo> fileStatusDocumentProjectFileInfoDocumentProjectFileInfo =
            (fileStatusDocument, projectFileInfoDocument) -> {
                ProjectFileInfo projectFileInfo = new ProjectFileInfo();
                projectFileInfo.setProjectFileType(fileStatusDocument.getProjectFileType());
                projectFileInfo.setBasePointId(projectFileInfoDocument.getBasePointId());
                projectFileInfo.setFileName(String.join(",", fileStatusDocument.getFileNames()));
                projectFileInfo.setFileLocation(String.join(",", fileStatusDocument.getFileLocations()));
                projectFileInfo.setAntennaHeight(projectFileInfoDocument.getAntennaHeight());
                projectFileInfo.setAntennaBrand(projectFileInfoDocument.getAntennaBrand());
                projectFileInfo.setAntennaModel(projectFileInfoDocument.getAntennaModel());
                projectFileInfo.setGpsCoordinatesLat(projectFileInfoDocument.getGpsCoordinatesLat());
                projectFileInfo.setGpsCoordinatesLon(projectFileInfoDocument.getGpsCoordinatesLon());
                projectFileInfo.setGpsCoordinatesZ(projectFileInfoDocument.getGpsCoordinatesZ());
                return projectFileInfo;
            };

    public static Function<ProjectFileInfo, ProjectFileInfoDocument> projectFileInfoProjectFileInfoDocument =
            projectFileInfo -> {
                ProjectFileInfoDocument projectFileInfoDocument = new ProjectFileInfoDocument();
                projectFileInfoDocument.setProjectFileType(projectFileInfo.getProjectFileType());
                projectFileInfoDocument.setBasePointId(projectFileInfoDocument.getBasePointId());
                Arrays.stream(projectFileInfo.getFileName().split(","))
                        .forEach(fileName -> projectFileInfoDocument.getFileNames().add(fileName));
                Arrays.stream(projectFileInfo.getFileLocation().split(","))
                        .forEach(fileLocation -> projectFileInfoDocument.getFileLocations().add(fileLocation));
                projectFileInfoDocument.setAntennaHeight(projectFileInfo.getAntennaHeight());
                projectFileInfoDocument.setAntennaBrand(projectFileInfo.getAntennaBrand());
                projectFileInfoDocument.setAntennaModel(projectFileInfo.getAntennaModel());
                projectFileInfoDocument.setGpsCoordinatesLat(projectFileInfo.getGpsCoordinatesLat());
                projectFileInfoDocument.setGpsCoordinatesLon(projectFileInfo.getGpsCoordinatesLon());
                projectFileInfoDocument.setGpsCoordinatesZ(projectFileInfo.getGpsCoordinatesZ());
                return projectFileInfoDocument;
            };

    public static BiFunction<ProjectDocument, Map<String, UserDetailDocument>, ProjectDocument> projectDocumentMapProjectDocumentBiFunction =
            (projectDocument, stringUserDetailDocumentMap) -> {
                UserDetailDocument userDetailDocument = stringUserDetailDocumentMap.get(projectDocument.getUserHref());
                projectDocument.setUserName(userDetailDocument.getUsername());
                projectDocument.setName(userDetailDocument.getName());
                projectDocument.setOrganization(userDetailDocument.getOrganization());
                return projectDocument;
            };
}
