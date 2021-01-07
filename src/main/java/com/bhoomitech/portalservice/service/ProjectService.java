package com.bhoomitech.portalservice.service;

import com.bhoomitech.portalservice.model.Project;
import com.bhoomitech.portalservice.repository.ProjectRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

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

    public Optional<Project> getProjectById(@NonNull Long projectId) {
        return projectRepository.findById(projectId);
    }

    public Optional<Project> getProjectByName(@NonNull String projectName) {
        return projectRepository.findByProjectName(projectName);
    }

    public Project saveOrUpdateProject(@NonNull Project project) {
        return projectRepository.save(project);
    }
}
