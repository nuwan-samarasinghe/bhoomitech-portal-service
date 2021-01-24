package com.bhoomitech.portalservice.service;

import com.bhoomitech.portalservice.common.StatusCodes;
import com.bhoomitech.portalservice.model.FileStatus;
import com.bhoomitech.portalservice.model.Project;
import com.bhoomitech.portalservice.model.ProjectFileInfo;
import com.bhoomitech.portalservice.repository.ProjectRepository;
import com.bhoomitech.portalservice.util.ProjectConverter;
import com.xcodel.commons.common.ResponseObject;
import com.xcodel.commons.common.ResponseStatus;
import com.xcodel.commons.mail.MailService;
import com.xcodel.commons.mail.model.Email;
import com.xcodel.commons.mail.model.MailConfiguration;
import com.xcodel.commons.project.ProjectDocument;
import com.xcodel.commons.project.ProjectFileInfoDocument;
import com.xcodel.commons.project.ProjectFileType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class ProjectService {

    public static final String AUTH_USER = "/auth/user/";
    private final ProjectRepository projectRepository;

    private final FileUploadService fileUploadService;

    private final AuthService authService;

    @Value("${app.custom-configs.email.name}")
    private String name;

    @Value("${app.custom-configs.email.address}")
    private String emailAddress;

    @Value("${app.custom-configs.email.password}")
    private String emailPassword;

    @Value("${app.custom-configs.email.smtp-host}")
    private String smtpHost;

    @Value("${app.custom-configs.email.smtp-port}")
    private Integer smtpPort;

    public ProjectService(ProjectRepository projectRepository,
                          FileUploadService fileUploadService,
                          AuthService authService) {
        this.projectRepository = projectRepository;
        this.fileUploadService = fileUploadService;
        this.authService = authService;
    }

    public List<Project> getProject() {
        return projectRepository.findAllByOrderByCreatedTimestampDesc();
    }

    @Transactional(rollbackFor = Exception.class)
    public ProjectDocument saveOrUpdateProject(@NonNull Project project) {
        return ProjectConverter.projectProjectDocumentFunction.apply(projectRepository.save(project), true);
    }

    public List<Project> getProjectByUserHref(String userHref) {
        return projectRepository.findAllByUserHrefOrderByCreatedTimestampDesc(userHref);
    }

    public ResponseObject checkProjectName(String projectName, String userHref) {
        ResponseObject responseObject = new ResponseObject();
        ResponseStatus responseStatus = new ResponseStatus();
        if (projectRepository.findByProjectNameAndUserHref(projectName, userHref).isPresent()) {
            responseObject.setResponseData("not available");
            responseStatus.setResultCode(StatusCodes.PROJECT_NAME_NOT_AVAILABLE.getStatusCode());
            responseStatus.setResultDescription(StatusCodes.PROJECT_NAME_NOT_AVAILABLE.getMessage());
        } else {
            responseStatus.setResultCode(StatusCodes.PROJECT_NAME_AVAILABLE.getStatusCode());
            responseStatus.setResultDescription(StatusCodes.PROJECT_NAME_AVAILABLE.getMessage());
            responseObject.setResponseData("available");
        }
        return responseObject;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createProjectFileInfo(
            String additionalDirStructure,
            String projectId,
            ProjectFileInfoDocument projectFileInfoDocument,
            ProjectFileType projectFileType,
            MultipartFile[] files) {
        Optional<Project> projectOptional = projectRepository.findById(Long.parseLong(projectId));
        if (projectOptional.isPresent()) {
            log.info("project name exists hence updating the info");
            Project project = projectOptional.get();
            if (project
                    .getFileInfos()
                    .stream()
                    .noneMatch(projectFileInfo -> projectFileInfo.getBasePointId().equals(projectFileInfoDocument.getBasePointId()))) {
                log.info("project is not having the same base point id");
                FileStatus fileStatus = this.fileUploadService.fileUpload(additionalDirStructure, files, project.getProjectName(), projectFileType);
                ProjectFileInfo projectFileInfo = ProjectConverter
                        .fileStatusDocumentProjectFileInfoDocumentProjectFileInfo
                        .apply(fileStatus, projectFileInfoDocument);
                project.getFileInfos().add(projectFileInfo);
                Project save = projectRepository.save(project);
                log.info("updated project {}", save);
                for (String fileName : fileStatus.getFileNames()) {
                    File deleteFile = new File(System.getProperty("user.dir") + "/" + fileName);
                    try {
                        log.info("deleting the uploaded file {} status {}", fileName, Files.deleteIfExists(deleteFile.toPath()));
                    } catch (IOException e) {
                        log.error("an error occurred while deleting a file", e);
                    }
                }

                projectFileInfoDocument.setMessage("successfully created");
            } else {
                projectFileInfoDocument.setMessage("cannot duplicate base point id in a single project");
            }
        } else {
            projectFileInfoDocument.setMessage("project not exists");
        }
    }

    public Project getProjectByProjectName(String projectName) {
        return projectRepository.findByProjectName(projectName).isPresent() ?
                projectRepository.findByProjectName(projectName).get() : new Project();
    }

    public boolean completeProject(@NonNull String projectId, @NonNull String success, String token) {
        AtomicBoolean updated = new AtomicBoolean(false);
        Optional<Project> optionalProject = projectRepository.findById(Long.parseLong(projectId));
        optionalProject.ifPresent(project -> {
            if ("SUCCESS".equalsIgnoreCase(success)) {
                project.setStatus("SUBMITTED");
                projectRepository.save(project);
                // sending mail
                String userId = StringUtils.substringAfterLast(project.getUserHref(), "/");
                Object user = this.authService.getUserById(token, userId);
                String username = ((LinkedHashMap) user).get("username").toString();
                String email = ((LinkedHashMap) user).get("email").toString();
                String mailBody = "<html>\n" +
                        "<p dir=\"ltr\" style=\"line-height:1.295;text-align: center;margin-top:0pt;margin-bottom:8pt;\"><span\n" +
                        "><span\n" +
                        "        style=\"font-size: 11pt; font-family: Calibri, sans-serif; color: rgb(0, 0, 0); background-color: transparent; font-weight: 700; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\">DATA UPLOADED TO THE SYSTEM SUCCESSFULLY!</span></span>\n" +
                        "</p>\n" +
                        "\n" +
                        "<p dir=\"ltr\" style=\"line-height:1.295;text-align: center;margin-top:0pt;margin-bottom:8pt;\"><span\n" +
                        "><span\n" +
                        "        style=\"font-size: 11pt; font-family: Calibri, sans-serif; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\">Thank you for using the Bhoomi-Tech GNSS data post-processing portal</span></span>\n" +
                        "</p>\n" +
                        "\n" +
                        "<p dir=\"ltr\" style=\"line-height:1.295;text-align: center;margin-top:0pt;margin-bottom:8pt;\"><span\n" +
                        "><span\n" +
                        "        style=\"font-size: 11pt; font-family: Calibri, sans-serif; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\">Please be kind enough to send your payment confirmation through WhatsApp, email, or fax</span></span>\n" +
                        "</p>\n" +
                        "\n" +
                        "<p dir=\"ltr\" style=\"line-height:1.295;text-align: center;margin-top:0pt;margin-bottom:8pt;\"><span\n" +
                        "><span\n" +
                        "        style=\"font-size: 11pt; font-family: Calibri, sans-serif; color: rgb(0, 0, 0); background-color: transparent; font-variant-numeric: normal; font-variant-east-asian: normal; vertical-align: baseline; white-space: pre-wrap;\">Your report will be ready within 48 hours</span></span>\n" +
                        "</p>\n" +
                        "</html>";

                sendMail(username, email, mailBody, "DATA UPLOADED TO BHOOMITECH GNSS");
                updated.set(true);
            } else if ("ERROR".equalsIgnoreCase(success)) {
                project.setStatus("ERROR");
                projectRepository.save(project);
                updated.set(true);
            }

        });
        return updated.get();
    }

    private Boolean sendMail(@NonNull String userName, @NonNull String toEmail, @NonNull String body, String subject) {
        MailConfiguration mailConfiguration = new MailConfiguration();
        mailConfiguration.setSmtpHost(smtpHost);
        mailConfiguration.setSmtpPort(smtpPort);
        mailConfiguration.setSmtpUserName(emailAddress);
        mailConfiguration.setSmtpPassword(emailPassword);

        Email email = new Email();
        email.setToName(userName);
        email.setToEmail(toEmail);
        email.setFromName(name);
        email.setFromEmail(emailAddress);
        email.setSubject(subject);
        email.setBody(body);

        boolean success = MailService.getMailService(mailConfiguration).sendMail(email);
        if (success)
            log.info("email sent success to {}", toEmail);
        else
            log.error("email sent failed to {}", toEmail);
        return success;
    }
}
