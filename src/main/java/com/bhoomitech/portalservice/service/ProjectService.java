package com.bhoomitech.portalservice.service;

import com.bhoomitech.portalservice.model.FileStatus;
import com.bhoomitech.portalservice.apidocs.project.ProjectDocument;
import com.bhoomitech.portalservice.apidocs.project.ProjectFileInfoDocument;
import com.bhoomitech.portalservice.model.Project;
import com.bhoomitech.portalservice.model.ProjectFileInfo;
import com.bhoomitech.portalservice.model.ProjectFileType;
import com.bhoomitech.portalservice.repository.ProjectRepository;
import com.bhoomitech.portalservice.util.ProjectConverter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final FileUploadService fileUploadService;

    public ProjectService(ProjectRepository projectRepository, FileUploadService fileUploadService) {
        this.projectRepository = projectRepository;
        this.fileUploadService = fileUploadService;
    }

    public List<Project> getProject() {
        return projectRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public ProjectDocument saveOrUpdateProject(@NonNull Project project, @NonNull String status) {
        return ProjectConverter.projectProjectDocumentFunction.apply(projectRepository.save(project));
    }

    public List<Project> getProjectByUserHref(String userHref) {
        return projectRepository.findAllByUserHref(userHref);
    }

    public ResponseEntity<String> checkProjectName(String projectName) {
        if (projectRepository.findByProjectName(projectName).isPresent()) {
            return ResponseEntity.badRequest().body("not available");
        }
        return ResponseEntity.ok("available");
    }

    public void createProjectFileInfo(
            String projectName,
            ProjectFileInfoDocument projectFileInfoDocument,
            ProjectFileType projectFileType,
            MultipartFile[] files) {
        Optional<Project> projectOptional = projectRepository.findByProjectName(projectName);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            if (!project
                    .getFileInfos()
                    .stream()
                    .filter(projectFileInfo -> {
                        return projectFileInfo.getBasePointId().equals(projectFileInfoDocument.getBasePointId());
                    }).findFirst().isPresent()) {
                FileStatus fileStatus = this.fileUploadService.fileUpload(files, projectName, projectFileType);
                ProjectFileInfo projectFileInfo = ProjectConverter
                        .fileStatusDocumentProjectFileInfoDocumentProjectFileInfo
                        .apply(fileStatus, projectFileInfoDocument);
                project.getFileInfos().add(projectFileInfo);
                projectRepository.save(project);
                projectFileInfoDocument.setMessage("successfully created");
            } else {
                projectFileInfoDocument.setMessage("cannot duplicate base point id in a single project");
            }
        } else {
            projectFileInfoDocument.setMessage("project not exists");
        }
    }
}
