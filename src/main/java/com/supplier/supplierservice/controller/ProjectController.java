package com.supplier.supplierservice.controller;

import com.supplier.supplierservice.apidocs.project.ProjectFileInfoRequestDocument;
import com.supplier.supplierservice.apidocs.project.ProjectFileInfoResponseDocument;
import com.supplier.supplierservice.service.ProjectFileInfoService;
import com.supplier.supplierservice.util.ProjectConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/project", name = "Project Controller")
public class ProjectController {

    private final ProjectFileInfoService projectFileInfoService;

    public ProjectController(ProjectFileInfoService projectFileInfoService) {
        this.projectFileInfoService = projectFileInfoService;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    @GetMapping(value = "/all", name = "Get All Project Details")
    private @ResponseBody
    List<ProjectFileInfoResponseDocument> getAllProjectFileInfoList() {
        return projectFileInfoService.getProjectInfos()
                .stream()
                .map(ProjectConverter.fileInfoResponseDocumentFunction)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/user", name = "Get All Project Details For Given User Href")
    private @ResponseBody
    List<ProjectFileInfoResponseDocument> getAllProjectFileInfoListForUserHref(@RequestBody String userHref) {
        return projectFileInfoService
                .getProjectInfosByUserHref(userHref)
                .stream()
                .map(ProjectConverter.fileInfoResponseDocumentFunction)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/create", name = "Create Project Details")
    private @ResponseBody
    ProjectFileInfoResponseDocument getAllProjectFileInfoList(@RequestBody ProjectFileInfoRequestDocument projectFileInfoRequestDocument) {
        return ProjectConverter.fileInfoResponseDocumentFunction
                .apply(projectFileInfoService
                        .saveOrUpdateProjectFileInfo(ProjectConverter.documentProjectFileInfoFunction
                                .apply(projectFileInfoRequestDocument)));
    }
}
