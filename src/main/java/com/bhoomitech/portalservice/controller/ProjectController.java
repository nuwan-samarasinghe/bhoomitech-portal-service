package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.apidocs.project.ProjectDocument;
import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoDocument;
import com.bhoomitech.portalservice.model.ProjectFileType;
import com.bhoomitech.portalservice.service.ProjectService;
import com.bhoomitech.portalservice.util.ProjectConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    List<ProjectDocument> getProjects(@RequestParam("projectOnly") boolean projectOnly) {
        List<ProjectDocument> projectDocuments = new ArrayList<>();
        projectService.getProject()
                .forEach(project ->
                        projectDocuments.add(ProjectConverter.projectProjectDocumentFunction.apply(project, projectOnly)));
        return projectDocuments;
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/project/{projectName}")
    public ResponseEntity<String> checkProjectNameIsAvailable(@PathVariable String projectName) {
        return projectService.checkProjectName(projectName);
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
            @RequestParam("projectName") String projectName,
            @RequestParam("projectFileType") ProjectFileType projectFileType,
            @RequestParam("files") MultipartFile[] files,
            ProjectFileInfoDocument projectFileInfoDocument
    ) {
        this.projectService.createProjectFileInfo(projectName, projectFileInfoDocument, projectFileType, files);
        return projectFileInfoDocument;
    }
}
