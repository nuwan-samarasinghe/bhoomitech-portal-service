package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.model.FileStatus;
import com.bhoomitech.portalservice.apidocs.project.ProjectDocument;
import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoDocument;
import com.bhoomitech.portalservice.model.ProjectFileType;
import com.bhoomitech.portalservice.service.FileUploadService;
import com.bhoomitech.portalservice.service.ProjectService;
import com.bhoomitech.portalservice.util.ProjectConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @GetMapping(value = "/project/all")
    public @ResponseBody
    List<ProjectDocument> getProjects() {
        return projectService.getProject()
                .stream()
                .map(ProjectConverter.projectProjectDocumentFunction)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/project")
    public ResponseEntity<String> checkProjectNameIsAvailable(@RequestParam("projectName") String projectName) {
        return projectService.checkProjectName(projectName);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project/user")
    public @ResponseBody
    List<ProjectDocument> getProjectsForUserHref(@RequestParam("userHref") String userHref) {
        return projectService
                .getProjectByUserHref(userHref)
                .stream()
                .map(ProjectConverter.projectProjectDocumentFunction)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project")
    public @ResponseBody
    ProjectDocument createProject(@RequestBody ProjectDocument projectFileInfoDocument) {
        return projectService
                .saveOrUpdateProject(ProjectConverter.projectDocumentProjectFunction
                        .apply(projectFileInfoDocument), CREATE);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PutMapping(value = "/project")
    public @ResponseBody
    ProjectDocument updateProject(@RequestBody ProjectDocument projectFileInfoDocument) {
        return projectService
                .saveOrUpdateProject(ProjectConverter.projectDocumentProjectFunction
                        .apply(projectFileInfoDocument), UPDATE);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project-info")
    public ProjectFileInfoDocument createProjectFileInfoDocument(
            @RequestParam("projectName") String projectName,
            @RequestParam("projectFileType") ProjectFileType projectFileType,
            @RequestParam("files") MultipartFile[] files,
            ProjectFileInfoDocument projectFileInfoDocument
    ) {
        this.projectService.createProjectFileInfo(projectName, projectFileInfoDocument, projectFileType, files);
        return projectFileInfoDocument;
    }
}
