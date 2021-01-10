package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.apidocs.project.ProjectDocument;
import com.bhoomitech.portalservice.service.ProjectService;
import com.bhoomitech.portalservice.util.ProjectConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProjectController {

    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping(value = "/project/all")
    public @ResponseBody
    List<ProjectDocument> getAllProjectFileInfoList() {
        return projectService.getProject()
                .stream()
                .map(ProjectConverter.projectProjectDocumentFunction)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/project")
    public ResponseEntity<String> checkProjectName(@RequestParam("projectName") String projectName) {
        return projectService.checkProjectName(projectName);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @GetMapping(value = "/project/user")
    public @ResponseBody
    List<ProjectDocument> getAllProjectFileInfoListForUserHref(@RequestParam("userHref") String userHref) {
        return projectService
                .getProjectByUserHref(userHref)
                .stream()
                .map(ProjectConverter.projectProjectDocumentFunction)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project")
    public @ResponseBody
    ProjectDocument createProjectFileInfoList(@RequestBody ProjectDocument projectFileInfoDocument) {
        return projectService
                .saveOrUpdateProject(ProjectConverter.projectDocumentProjectFunction
                        .apply(projectFileInfoDocument), CREATE);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PutMapping(value = "/project")
    public @ResponseBody
    ProjectDocument updateProjectFileInfoList(@RequestBody ProjectDocument projectFileInfoDocument) {
        return projectService
                .saveOrUpdateProject(ProjectConverter.projectDocumentProjectFunction
                        .apply(projectFileInfoDocument), UPDATE);
    }
}
