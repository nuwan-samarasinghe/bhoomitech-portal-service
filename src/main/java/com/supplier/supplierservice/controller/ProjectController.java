package com.supplier.supplierservice.controller;

import com.supplier.supplierservice.model.ProjectFileInfo;
import com.supplier.supplierservice.service.ProjectFileInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    List<ProjectFileInfo> getAllProjectFileInfoList() {
        return projectFileInfoService.getProjectInfos();
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/user", name = "Get All Project Details For Given User Href")
    private @ResponseBody
    List<ProjectFileInfo> getAllProjectFileInfoListForUserHref(@RequestBody String userHref) {
        return projectFileInfoService.getProjectInfosByUserHref(userHref);
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/create", name = "Create Project Details")
    private @ResponseBody
    ProjectFileInfo getAllProjectFileInfoList(@RequestBody ProjectFileInfo projectFileInfo) {
        return projectFileInfoService.saveOrUpdateProjectFileInfo(projectFileInfo);
    }
}
