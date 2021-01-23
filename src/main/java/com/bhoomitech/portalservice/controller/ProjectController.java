package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.service.AuthService;
import com.bhoomitech.portalservice.service.ProjectService;
import com.bhoomitech.portalservice.util.ProjectConverter;
import com.bhoomitech.portalservice.util.SecretUtil;
import com.xcodel.commons.auth.userdetail.UserDetailDocument;
import com.xcodel.commons.project.ProjectDocument;
import com.xcodel.commons.project.ProjectFileInfoDocument;
import com.xcodel.commons.project.ProjectFileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RestController
public class ProjectController {

    public static final String CREATE = "CREATE";
    public static final String UPDATE = "UPDATE";
    public static final String AUTH_USER = "/auth/user/";
    private final ProjectService projectService;
    private final AuthService authService;

    public ProjectController(
            ProjectService projectService, AuthService authService) {
        this.projectService = projectService;
        this.authService = authService;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @PostMapping(value = "/project/all")
    public @ResponseBody
    List<ProjectDocument> getProjects(
            @RequestParam("projectOnly") boolean projectOnly,
            @RequestHeader("Authorization") String token
    ) {
        List<ProjectDocument> projectDocuments = new ArrayList<>();
        Map<String, UserDetailDocument> userDetailMap = new HashMap<>();
        for (Object map : this.authService.getAllAuthDetails(token)) {
            UserDetailDocument userDetailDocument = new UserDetailDocument();
            userDetailDocument.setName(((LinkedHashMap) map).get("name").toString());
            userDetailDocument.setOrganization(((LinkedHashMap) map).get("organization").toString());
            userDetailDocument.setUsername(((LinkedHashMap) map).get("username").toString());
            userDetailMap.put(AUTH_USER + SecretUtil.decode(((LinkedHashMap) map).get("id").toString()), userDetailDocument);
        }
        projectService.getProject()
                .forEach(project ->
                        projectDocuments.add(
                                ProjectConverter.projectProjectDocumentFunction.apply(project, projectOnly))
                );
        List<ProjectDocument> userDetails = new ArrayList<>();
        projectDocuments
                .forEach(projectDocument ->
                        userDetails.add(ProjectConverter.projectDocumentMapProjectDocumentBiFunction.apply(projectDocument, userDetailMap)));
        return userDetails;
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
