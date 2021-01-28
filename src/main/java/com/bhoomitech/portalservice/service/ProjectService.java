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

    @Value("${app.custom-configs.email.cc.address}")
    private String ccEmail;

    @Value("${app.custom-configs.email.cc.name}")
    private String ccName;

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

    public void checkProjectName(String projectName, String userHref, ResponseObject responseObject) {
        ResponseStatus responseStatus = new ResponseStatus();
        if (projectRepository.findByProjectNameAndUserHref(projectName, userHref).isPresent()) {
            responseObject.setResponseData("not available");
            responseStatus.setResultCode(StatusCodes.PROJECT_NAME_AVAILABLE_OK.getStatusCode());
            responseStatus.setResultDescription(StatusCodes.PROJECT_NAME_AVAILABLE_OK.getMessage());
        } else {
            responseStatus.setResultCode(StatusCodes.PROJECT_NAME_NOT_AVAILABLE_ERROR.getStatusCode());
            responseStatus.setResultDescription(StatusCodes.PROJECT_NAME_NOT_AVAILABLE_ERROR.getMessage());
            responseObject.setResponseData("available");
        }
        responseObject.setResponseStatus(responseStatus);
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
                        "<p><span\n" +
                        "        data-sheets-userformat=\"{&quot;2&quot;:573,&quot;3&quot;:{&quot;1&quot;:0},&quot;5&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;6&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;7&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;8&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;12&quot;:0}\"\n" +
                        "        data-sheets-value=\"{&quot;1&quot;:2,&quot;2&quot;:&quot;Project sucess email content \\n\\nSubject : 'Bhoomitech GNSS portal project creation'\\n\\nHi User, \\n\\nYour new project is now created \\nProject details \\nName\\nCreated Date\\nPrice\\nOther details\\n\\nPlease make your payments to;\\nAccount Number        - 0140-0077152-4002\\nAccounts Name                - BhoomiTech (PVT) Ltd\\nBank Name                - Seylan Bank\\nBranch                        - Dehiwala\\n\\nPlease forward a copy of the payment deposit slip/e-receipt copy to one of the following\\nWhatsApp - 074 017 7178\\nE-mail – gnsspp@bhoomitech.com\\nFax – 011 2713088\\nFor further information contact us on +94 74 017 7178 (Dinith)\\n&quot;}\"\n" +
                        "        style=\"color: rgb(0, 0, 0); font-size: 10pt; font-family: Arial;\">Hi <strong>{user_name}</strong>,<br/>\n" +
                        "<br/>\n" +
                        "Your new project is now created</span></p>\n" +
                        "\n" +
                        "<p><br/>\n" +
                        "    <span data-sheets-userformat=\"{&quot;2&quot;:573,&quot;3&quot;:{&quot;1&quot;:0},&quot;5&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;6&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;7&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;8&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;12&quot;:0}\"\n" +
                        "          data-sheets-value=\"{&quot;1&quot;:2,&quot;2&quot;:&quot;Project sucess email content \\n\\nSubject : 'Bhoomitech GNSS portal project creation'\\n\\nHi User, \\n\\nYour new project is now created \\nProject details \\nName\\nCreated Date\\nPrice\\nOther details\\n\\nPlease make your payments to;\\nAccount Number        - 0140-0077152-4002\\nAccounts Name                - BhoomiTech (PVT) Ltd\\nBank Name                - Seylan Bank\\nBranch                        - Dehiwala\\n\\nPlease forward a copy of the payment deposit slip/e-receipt copy to one of the following\\nWhatsApp - 074 017 7178\\nE-mail – gnsspp@bhoomitech.com\\nFax – 011 2713088\\nFor further information contact us on +94 74 017 7178 (Dinith)\\n&quot;}\"\n" +
                        "          style=\"color: rgb(0, 0, 0); font-size: 10pt; font-family: Arial;\">Project details</span></p>\n" +
                        "\n" +
                        "<p><br/>\n" +
                        "    <span data-sheets-userformat=\"{&quot;2&quot;:573,&quot;3&quot;:{&quot;1&quot;:0},&quot;5&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;6&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;7&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;8&quot;:{&quot;1&quot;:[{&quot;1&quot;:2,&quot;2&quot;:0,&quot;5&quot;:{&quot;1&quot;:2,&quot;2&quot;:0}},{&quot;1&quot;:0,&quot;2&quot;:0,&quot;3&quot;:3},{&quot;1&quot;:1,&quot;2&quot;:0,&quot;4&quot;:1}]},&quot;12&quot;:0}\"\n" +
                        "          data-sheets-value=\"{&quot;1&quot;:2,&quot;2&quot;:&quot;Project sucess email content \\n\\nSubject : 'Bhoomitech GNSS portal project creation'\\n\\nHi User, \\n\\nYour new project is now created \\nProject details \\nName\\nCreated Date\\nPrice\\nOther details\\n\\nPlease make your payments to;\\nAccount Number        - 0140-0077152-4002\\nAccounts Name                - BhoomiTech (PVT) Ltd\\nBank Name                - Seylan Bank\\nBranch                        - Dehiwala\\n\\nPlease forward a copy of the payment deposit slip/e-receipt copy to one of the following\\nWhatsApp - 074 017 7178\\nE-mail – gnsspp@bhoomitech.com\\nFax – 011 2713088\\nFor further information contact us on +94 74 017 7178 (Dinith)\\n&quot;}\"\n" +
                        "          style=\"color: rgb(0, 0, 0); font-size: 10pt; font-family: Arial;\">Name: <strong>{project_name}</strong><br/>\n" +
                        "Created Date: {<strong>creation_date}</strong><br/>\n" +
                        "Price: {<strong>price}</strong><br/>\n" +
                        "<br/>\n" +
                        "Please make your payments to;<br/>\n" +
                        "Account Number - 0140-0077152-4002<br/>\n" +
                        "Accounts Name - BhoomiTech (PVT) Ltd<br/>\n" +
                        "Bank Name - Seylan Bank<br/>\n" +
                        "Branch - Dehiwala<br/>\n" +
                        "<br/>\n" +
                        "Please forward a copy of the payment deposit slip/e-receipt copy to one of the following<br/>\n" +
                        "WhatsApp - 074 017 7178<br/>\n" +
                        "E-mail &ndash; gnsspp@bhoomitech.com<br/>\n" +
                        "Fax &ndash; 011 2713088<br/>\n" +
                        "For further information contact us on +94 74 017 7178 (Dinith)</span><br/>\n" +
                        "    &nbsp;</p>\n" +
                        "\n" +
                        "</html>";

                String replacedBody = mailBody.replace("{user_name}", username)
                        .replace("{project_name}", project.getProjectName())
                        .replace("{creation_date}", project.getCreatedTimestamp().toString())
                        .replace("{price}", project.getPrice());

                sendMail(username, email, replacedBody, "Bhoomitech GNSS portal project creation");
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

        email.setCcName(ccName);
        email.setCcEmail(ccEmail);

        email.setFromName(name);
        email.setFromEmail(emailAddress);
        email.setSubject(subject);
        email.setBody(body);

        boolean success = MailService.getMailService(mailConfiguration).sendMailWithCC(email);
        if (success)
            log.info("email sent success to {}", toEmail);
        else
            log.error("email sent failed to {}", toEmail);
        return success;
    }
}
