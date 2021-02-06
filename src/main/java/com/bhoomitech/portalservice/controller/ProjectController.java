package com.bhoomitech.portalservice.controller;

import com.bhoomitech.portalservice.common.StatusCodes;
import com.bhoomitech.portalservice.model.Project;
import com.bhoomitech.portalservice.service.AuthService;
import com.bhoomitech.portalservice.service.ProjectService;
import com.bhoomitech.portalservice.util.ProjectConverter;
import com.bhoomitech.portalservice.util.SecretUtil;
import com.xcodel.commons.auth.userdetail.UserDetailDocument;
import com.xcodel.commons.common.ResponseObject;
import com.xcodel.commons.common.ResponseStatus;
import com.xcodel.commons.project.ProjectDocument;
import com.xcodel.commons.project.ProjectFileInfoDocument;
import com.xcodel.commons.project.ProjectFileType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RestController
public class ProjectController {

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
    ResponseObject getProjects(
            @RequestParam("projectOnly") boolean projectOnly,
            @RequestHeader("Authorization") String token
    ) {
        ResponseObject responseObject = new ResponseObject();
        //find the project on the db
        List<ProjectDocument> projectDocuments = new ArrayList<>();
        try {
            projectService.getProject()
                    .forEach(project -> projectDocuments.add(ProjectConverter.projectProjectDocumentFunction
                            .apply(project, projectOnly)));
        } catch (Exception e) {
            log.error("error occurred while accessing the the project details", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_DETAILS_NOT_AVAILABLE_ERROR);
            return responseObject;
        }
        // getting the user information
        Map<String, UserDetailDocument> userDetailMap = new HashMap<>();
        try {
            for (Object map : this.authService.getAllAuthDetails(token)) {
                UserDetailDocument userDetailDocument = new UserDetailDocument();
                userDetailDocument.setName(((LinkedHashMap) map).get("name").toString());
                userDetailDocument.setOrganization(((LinkedHashMap) map).get("organization").toString());
                userDetailDocument.setUsername(((LinkedHashMap) map).get("username").toString());
                userDetailMap.put(AUTH_USER + SecretUtil.decode(((LinkedHashMap) map).get("id").toString()), userDetailDocument);
            }
        } catch (Exception e) {
            log.error("An error occurred while accessing the the user details", e);
            createResponseObject(responseObject, StatusCodes.USER_DETAILS_NOT_AVAILABLE_ERROR);
            return responseObject;
        }

        List<ProjectDocument> userDetails = new ArrayList<>();
        projectDocuments
                .forEach(projectDocument -> userDetails.add(ProjectConverter.projectDocumentMapProjectDocumentBiFunction
                        .apply(projectDocument, userDetailMap)));

        createResponseObject(responseObject, StatusCodes.PROJECT_DETAILS_AVAILABLE_OK);
        responseObject.setResponseData(userDetails);
        return responseObject;
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project/validity")
    public ResponseObject checkProjectNameIsAvailable(
            @RequestParam("projectName") String projectName,
            @RequestParam("userHref") String userHref) {
        ResponseObject responseObject = new ResponseObject();
        if (Objects.isNull(projectName) || Objects.isNull(userHref)) {
            createResponseObject(responseObject, StatusCodes.DATA_VALIDATION_ERROR);
            return responseObject;
        }
        try {
            projectService.checkProjectName(projectName, userHref, responseObject);
            return responseObject;
        } catch (Exception e) {
            log.error("An error occurred while checking the validity.", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_DETAILS_NOT_AVAILABLE_ERROR);
            return responseObject;
        }
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project/detail")
    public ResponseObject getProjectInformation(@RequestParam("projectId") Long projectId) {
        ResponseObject responseObject = new ResponseObject();
        if (Objects.isNull(projectId)) {
            createResponseObject(responseObject, StatusCodes.DATA_VALIDATION_ERROR);
            return responseObject;
        }
        try {
            Project project = projectService.getProjectById(projectId);
            createResponseObject(responseObject, StatusCodes.PROJECT_DETAILS_AVAILABLE_OK);
            responseObject.setResponseData(ProjectConverter.projectProjectDocumentFunction.apply(project, false));
            return responseObject;
        } catch (Exception e) {
            log.error("An error occurred while getting the project details.", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_DETAILS_NOT_AVAILABLE_ERROR);
            return responseObject;
        }
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project/user")
    public @ResponseBody
    ResponseObject getProjectsForUserHref(@RequestParam("userHref") String userHref,
                                          @RequestParam("projectOnly") boolean projectOnly) {
        ResponseObject responseObject = new ResponseObject();
        if (Objects.isNull(userHref)) {
            createResponseObject(responseObject, StatusCodes.DATA_VALIDATION_ERROR);
            return responseObject;
        }
        try {
            List<ProjectDocument> projectDocuments = new ArrayList<>();
            projectService
                    .getProjectByUserHref(userHref)
                    .forEach(project -> projectDocuments.add(ProjectConverter.projectProjectDocumentFunction
                            .apply(project, projectOnly)));
            createResponseObject(responseObject, StatusCodes.PROJECT_DETAILS_AVAILABLE_OK);
            responseObject.setResponseData(projectDocuments);
            return responseObject;
        } catch (Exception e) {
            log.error("An error occurred while getting the project details.", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_DETAILS_NOT_AVAILABLE_ERROR);
            return responseObject;
        }
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project")
    public @ResponseBody
    ResponseObject createProject(@RequestBody ProjectDocument projectDocument) {
        ResponseObject responseObject = new ResponseObject();
        try {
            createResponseObject(responseObject, StatusCodes.PROJECT_CREATION_OK);
            responseObject.setResponseData(projectService
                    .saveOrUpdateProject(ProjectConverter.projectDocumentProjectFunction
                            .apply(projectDocument)));
            return responseObject;
        } catch (Exception e) {
            log.error("An error occurred while creating the project.", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_CREATION_ERROR);
            return responseObject;
        }
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PutMapping(value = "/project")
    public @ResponseBody
    ResponseObject updateProject(@RequestBody ProjectDocument projectDocument) {
        ResponseObject responseObject = new ResponseObject();
        try {
            createResponseObject(responseObject, StatusCodes.PROJECT_UPDATE_OK);
            responseObject.setResponseData(projectService
                    .saveOrUpdateProject(ProjectConverter.projectDocumentProjectFunction
                            .apply(projectDocument)));
            return responseObject;
        } catch (Exception e) {
            log.error("An error occurred while updating the project.", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_UPDATE_ERROR);
            return responseObject;
        }
    }

    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @DeleteMapping(value = "/project/{projectId}")
    public @ResponseBody
    ResponseObject deleteProject(@PathVariable("projectId") Long projectId) {
        ResponseObject responseObject = new ResponseObject();
        try {
            createResponseObject(responseObject, StatusCodes.PROJECT_DELETE_OK);
            projectService.deleteProject(projectId, responseObject);
            return responseObject;
        } catch (Exception e) {
            log.error("An error occurred while deleting the project.", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_DELETE_ERROR);
            return responseObject;
        }
    }

    /*@PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project-info")
    public ResponseObject createProjectFileInfoDocument(
            @RequestParam("additionalDirStructure") String additionalDirStructure,
            @RequestParam("projectId") String projectId,
            @RequestParam("projectFileType") ProjectFileType projectFileType,
            @RequestParam("files") MultipartFile[] files,
            ProjectFileInfoDocument projectFileInfoDocument
    ) {
        log.info("project additional dir structure is {}", additionalDirStructure);
        log.info("project id is {}", projectId);
        log.info("project type is {}", projectFileType);
        log.info("project files are {}", files.length);
        ResponseObject responseObject = new ResponseObject();
        if (Objects.isNull(projectId) ||
                Objects.isNull(projectFileType) ||
                files.length == 0 ||
                Objects.isNull(projectFileInfoDocument)) {
            createResponseObject(responseObject, StatusCodes.DATA_VALIDATION_ERROR);
            return responseObject;
        }
        if (ProjectFileType.KNOWN_FILE.getFileType().equals(projectFileType.getFileType())) {
            if (StringUtils.isAnyEmpty(projectFileInfoDocument.getGpsCoordinatesLat(),
                    projectFileInfoDocument.getGpsCoordinatesLon(),
                    projectFileInfoDocument.getGpsCoordinatesZ())
                    && StringUtils.isAnyEmpty(projectFileInfoDocument.getGpsCoordinatesX(),
                    projectFileInfoDocument.getGpsCoordinatesY(),
                    projectFileInfoDocument.getGpsCoordinatesZ())) {
                createResponseObject(responseObject, StatusCodes.PROJECT_CREATION_VALIDATION_ERROR);
                return responseObject;
            }
        }
        try {
            this.projectService
                    .createProjectFileInfo(
                            additionalDirStructure,
                            projectId,
                            projectFileInfoDocument,
                            projectFileType,
                            files);
            createResponseObject(responseObject, StatusCodes.PROJECT_INFO_CREATION_OK);
            responseObject.setResponseData(projectFileInfoDocument);
            return responseObject;
        } catch (Exception e) {
            log.error("An error occurred while creating the project info.", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_INFO_CREATION_ERROR);
            return responseObject;
        }
    }*/


    @PreAuthorize("hasRole('ROLE_admin') or hasRole('ROLE_operator')")
    @PostMapping(value = "/project/complete")
    public ResponseObject completeProject(
            @RequestParam("projectId") String projectId,
            @RequestParam("success") String success,
            @RequestHeader("Authorization") String token
    ) {
        log.info("project id is {}", projectId);
        log.info("project success? {}", success);
        ResponseObject responseObject = new ResponseObject();
        if (Objects.isNull(projectId) ||
                Objects.isNull(success) ||
                Objects.isNull(token)) {
            createResponseObject(responseObject, StatusCodes.DATA_VALIDATION_ERROR);
            return responseObject;
        }
        try {
            boolean updated = this.projectService.completeProject(projectId, success, token);
            if (!updated) {
                createResponseObject(responseObject, StatusCodes.PROJECT_CREATION_COMPLETED_OK);
                responseObject.setResponseData("not updated");
                return responseObject;
            }
            createResponseObject(responseObject, StatusCodes.PROJECT_CREATION_COMPLETED_ERROR);
            responseObject.setResponseData("updated");
            return responseObject;
        } catch (Exception e) {
            log.error("An error occurred while creating the project info.", e);
            createResponseObject(responseObject, StatusCodes.PROJECT_CREATION_COMPLETED_ERROR);
            responseObject.setResponseData("not updated");
            return responseObject;
        }
    }

    private void createResponseObject(ResponseObject responseObject, StatusCodes dataValidationError) {
        ResponseStatus responseStatus = new ResponseStatus();
        responseStatus.setResultCode(dataValidationError.getStatusCode());
        responseStatus.setResultDescription(dataValidationError.getMessage());
        responseObject.setResponseStatus(responseStatus);
    }
}
