package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.apidocs.project.ProjectDocument;
import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoDocument;
import com.bhoomitech.portalservice.model.ProjectFileType;
import com.bhoomitech.portalservice.service.ProjectService;
import com.bhoomitech.portalservice.util.ProjectConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class ProjectController {

    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";
    private final ProjectService projectService;

    public ProjectController(
            ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping(value = "/project/all")
    public @ResponseBody
    List<ProjectDocument> getProjects(@RequestParam("projectOnly") boolean projectOnly) {
        List<ProjectDocument> projectDocuments = new ArrayList<>();
        projectService.getProject()
                .forEach(project ->
                        projectDocuments.add(ProjectConverter.projectProjectDocumentFunction.apply(project, projectOnly)));
        return projectDocuments;
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project/validity")
    public ResponseEntity<String> checkProjectNameIsAvailable(
            @RequestParam("projectName") String projectName,
            @RequestParam("userHref") String userHref) {
        return projectService.checkProjectName(projectName, userHref);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project/detail")
    public ProjectDocument getProjectInformation(@RequestParam("projectName") String projectName) {
        return ProjectConverter.projectProjectDocumentFunction.apply(projectService
                .getProjectByProjectName(projectName), false);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project/user")
    public @ResponseBody
    List<ProjectDocument> getProjectsForUserHref(@RequestParam("userHref") String userHref,
                                                 @RequestParam("projectOnly") boolean projectOnly) {
        List<ProjectDocument> projectDocuments = new ArrayList<>();
        projectService
                .getProjectByUserHref(userHref)
                .forEach(project ->
                        projectDocuments.add(ProjectConverter.projectProjectDocumentFunction.apply(project, projectOnly)));
        return projectDocuments;
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project")
    public @ResponseBody
    ProjectDocument createProject(@RequestBody ProjectDocument projectFileInfoDocument) {
        return projectService
                .saveOrUpdateProject(ProjectConverter.projectDocumentProjectFunction
                        .apply(projectFileInfoDocument));
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PutMapping(value = "/project")
    public @ResponseBody
    ProjectDocument updateProject(@RequestBody ProjectDocument projectFileInfoDocument) {
        return projectService
                .saveOrUpdateProject(ProjectConverter.projectDocumentProjectFunction
                        .apply(projectFileInfoDocument));
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project-info")
    public ProjectFileInfoDocument createProjectFileInfoDocument(
            @RequestParam("additionalDirStructure") String additionalDirStructure,
            @RequestParam("projectName") String projectName,
            @RequestParam("projectFileType") ProjectFileType projectFileType,
            @RequestParam("files") MultipartFile[] files,
            ProjectFileInfoDocument projectFileInfoDocument
    ) {
        log.info("project additional dir structure is {}", additionalDirStructure);
        log.info("project name is {}", projectName);
        log.info("project type is {}", projectFileType);
        log.info("project files are {}", files.length);
        this.projectService.createProjectFileInfo(additionalDirStructure, projectName, projectFileInfoDocument, projectFileType, files);
        return projectFileInfoDocument;
    }
}
