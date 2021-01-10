package com.bhoomitech.portalservice.service;

import com.bhoomitech.portalservice.apidocs.project.ProjectDocument;
import com.bhoomitech.portalservice.model.Project;
import com.bhoomitech.portalservice.repository.ProjectRepository;
import com.bhoomitech.portalservice.util.ProjectConverter;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
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
}
