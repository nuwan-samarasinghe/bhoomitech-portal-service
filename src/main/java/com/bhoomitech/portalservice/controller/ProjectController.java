package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoRequestDocument;
import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoResponseDocument;
import com.bhoomitech.portalservice.service.ProjectFileInfoService;
import com.bhoomitech.portalservice.util.ProjectConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

    private final ProjectFileInfoService projectFileInfoService;

    public ProjectController(ProjectFileInfoService projectFileInfoService) {
        this.projectFileInfoService = projectFileInfoService;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping(value = "/all")
    public @ResponseBody
    List<ProjectFileInfoResponseDocument> getAllProjectFileInfoList() {
        return projectFileInfoService.getProjectInfos()
                .stream()
                .map(ProjectConverter.fileInfoResponseDocumentFunction)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/user")
    public @ResponseBody
    List<ProjectFileInfoResponseDocument> getAllProjectFileInfoListForUserHref(@RequestBody String userHref) {
        return projectFileInfoService
                .getProjectInfosByUserHref(userHref)
                .stream()
                .map(ProjectConverter.fileInfoResponseDocumentFunction)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/create")
    public @ResponseBody
    ProjectFileInfoResponseDocument getAllProjectFileInfoList(@RequestBody ProjectFileInfoRequestDocument projectFileInfoRequestDocument) {
        return ProjectConverter.fileInfoResponseDocumentFunction
                .apply(projectFileInfoService
                        .saveOrUpdateProjectFileInfo(ProjectConverter.documentProjectFileInfoFunction
                                .apply(projectFileInfoRequestDocument)));
    }
}
